package com.ssafy.neegongnaegong.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSource
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupDetailMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupNoticeDetailInfoMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupVoteDetailInfoMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyLogByTagInfoMapper.toDomain
import com.ssafy.neegongnaegong.data.paging.MemberStudyContentsPagingSource
import com.ssafy.neegongnaegong.data.paging.MyStudyGroupListPagingSource
import com.ssafy.neegongnaegong.data.paging.StudyGroupNoticeListPagingSource
import com.ssafy.neegongnaegong.data.paging.StudyGroupVoteListPagingSource
import com.ssafy.neegongnaegong.domain.model.studygroup.MyStudyGroupInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.NoticeHistoryInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupDetailInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupNoticeDetailInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteDetailInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo
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

        override fun getMemberStudyContents(request: StudyMemberInfo): Flow<PagingData<StudyContentInfo>> =
            Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = {
                    MemberStudyContentsPagingSource(
                        dataSource,
                        request.studyGroupId,
                        request.targetUserId,
                    )
                },
            ).flow

        override fun getVoteList(request: Long): Flow<PagingData<VoteHistoryInfo>> =
            Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = {
                    StudyGroupVoteListPagingSource(
                        dataSource,
                        request,
                    )
                },
            ).flow

        override fun getNoticeList(request: Long): Flow<PagingData<NoticeHistoryInfo>> =
            Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = {
                    StudyGroupNoticeListPagingSource(
                        dataSource,
                        request,
                    )
                },
            ).flow

        override fun getNoticeDetail(
            studyGroupId: Long,
            noticeId: Long,
        ): Flow<StudyGroupNoticeDetailInfo> = dataSource.getStudyGroupNoticeDetail(studyGroupId, noticeId).map { it.toDomain() }

        override fun deleteNoticeDetail(
            studyGroupId: Long,
            noticeId: Long,
        ): Flow<Unit> = dataSource.deleteNoticeDetail(studyGroupId, noticeId).flowOn(ioDispatcher)

        override fun getVoteDetail(voteId: Long): Flow<StudyGroupVoteDetailInfo> =
            dataSource.getStudyGroupVoteDetail(voteId).map { it.toDomain() }.flowOn(ioDispatcher)

        override fun deleteVoteDetail(
            studyGroupId: Long,
            voteId: Long,
        ): Flow<Unit> = dataSource.deleteVoteDetail(studyGroupId, voteId).flowOn(ioDispatcher)

        override fun castVote(
            studyGroupId: Long,
            voteId: Long,
            voteItems: List<String>,
        ): Flow<StudyGroupVoteDetailInfo> =
            dataSource.castVote(studyGroupId, voteId, voteItems).map { it.toDomain() }
                .flowOn(ioDispatcher)

        override fun addNewVoteOption(
            studyGroupId: Long,
            voteId: Long,
            voteItem: String,
        ): Flow<StudyGroupVoteDetailInfo> =
            dataSource.addNewVoteOption(studyGroupId, voteId, voteItem).map { it.toDomain() }
                .flowOn(ioDispatcher)

        override fun approveStudyGroupJoin(
            studyGroupId: Long,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> =
            dataSource.approveStudyGroupJoin(
                studyGroupId = studyGroupId,
                userId = userId,
                notificationId = notificationId,
            ).flowOn(context = ioDispatcher)

        override fun rejectStudyGroupJoin(
            studyGroupId: Long,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> =
            dataSource.rejectStudyGroupJoin(
                studyGroupId = studyGroupId,
                userId = userId,
                notificationId = notificationId,
            ).flowOn(context = ioDispatcher)

        override fun getStudyGroupDetail(studyGroupId: Long): Flow<StudyGroupDetailInfo> =
            dataSource.getStudyGroupDetail(studyGroupId = studyGroupId).map { it.toDomain() }
                .flowOn(context = ioDispatcher)

        override fun getMyStudyGroupList(size: Int): Flow<PagingData<MyStudyGroupInfo>> =
            Pager(
                config = PagingConfig(pageSize = size, enablePlaceholders = false),
                pagingSourceFactory = {
                    MyStudyGroupListPagingSource(
                        dataSource,
                    )
                },
            ).flow.flowOn(ioDispatcher)
    }
