package com.ssafy.neegongnaegong.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSource
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyLogByTagInfoMapper.toDomain
import com.ssafy.neegongnaegong.data.paging.MemberStudyContentsPagingSource
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StudyGroupRepositoryImpl
@Inject
constructor(
    private val dataSource: NetworkStudyGroupDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : StudyGroupRepository {
    override fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagInfo>> =
        dataSource.getMemberStudyLogsByTag(request).map { tagList -> tagList.toDomain() }

    override fun getMemberStudyContents(request: StudyMemberInfo):
            Flow<PagingData<StudyContentInfo>> =
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                MemberStudyContentsPagingSource(
                    dataSource,
                    request.studyGroupId,
                    request.targetUserId
                )
            }
        ).flow


    override fun approveStudyGroupJoin(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?
    ): Flow<Unit> = dataSource.approveStudyGroupJoin(
        studyGroupId = studyGroupId,
        userId = userId,
        notificationId = notificationId
    ).flowOn(context = ioDispatcher)

    override fun rejectStudyGroupJoin(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?
    ): Flow<Unit> = dataSource.rejectStudyGroupJoin(
        studyGroupId = studyGroupId,
        userId = userId,
        notificationId = notificationId
    ).flowOn(context = ioDispatcher)
}
