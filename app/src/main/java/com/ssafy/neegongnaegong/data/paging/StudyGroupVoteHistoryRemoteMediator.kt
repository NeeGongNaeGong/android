package com.ssafy.neegongnaegong.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSource
import com.ssafy.neegongnaegong.data.local.database.NeeGongNaeGongDatabase
import com.ssafy.neegongnaegong.data.local.database.dao.StudyGroupVoteHistoryRemoteKeyDao
import com.ssafy.neegongnaegong.data.local.database.entity.StudyGroupVoteHistory
import com.ssafy.neegongnaegong.data.local.database.entity.StudyGroupVoteHistoryRemoteKey
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupVoteHistoryInfoMapper.toEntity
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteListRequest
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class StudyGroupVoteHistoryRemoteMediator
    @AssistedInject
    constructor(
        @Assisted val studyGroupId: Long,
        private val dataSource: NetworkStudyGroupDataSource,
        private val database: NeeGongNaeGongDatabase,
    ) : RemoteMediator<Int, StudyGroupVoteHistory>() {
        private val remoteKeyDao: StudyGroupVoteHistoryRemoteKeyDao =
            database.studyGroupVoteHistoryRemoteKeyDao()
        private val studyGroupVoteHistoryDao = database.studyGroupVoteHistoryDao()

        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, StudyGroupVoteHistory>,
        ): MediatorResult {
            val remoteKey =
                when (loadType) {
                    LoadType.REFRESH -> null
                    LoadType.PREPEND -> return MediatorResult.Success(true)
                    LoadType.APPEND -> remoteKeyDao.getNextKey(studyGroupId)
                }

            return runCatching {
                val response =
                    dataSource.getStudyGroupVoteList(
                        StudyGroupVoteListRequest(
                            studyGroupId = studyGroupId,
                            cursorId = remoteKey?.cursorId,
                            cursorTime = remoteKey?.cursorTime,
                        ),
                    ).first()

                val endOfPaginationReached: Boolean = !response.hasNext

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        remoteKeyDao.deleteStudyGroupIdKey(studyGroupId)
                        studyGroupVoteHistoryDao.deleteVoteHistory(studyGroupId)
                    }

                    remoteKeyDao.insertOrReplace(
                        StudyGroupVoteHistoryRemoteKey(
                            studyGroupId = studyGroupId,
                            cursorTime = response.cursorCreatedAt,
                            cursorId = response.cursorId,
                        ),
                    )

                    studyGroupVoteHistoryDao.insertVoteHistory(response.content.toEntity(studyGroupId))
                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }.getOrElse { throwable: Throwable ->
                MediatorResult.Error(throwable = throwable)
            }
        }
    }

@AssistedFactory
interface GetStudyGroupVoteHistoryRemoteMediatorFactory {
    fun create(studyGroupId: Long): StudyGroupVoteHistoryRemoteMediator
}
