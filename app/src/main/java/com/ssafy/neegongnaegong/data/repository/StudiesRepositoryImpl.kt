package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudiesDataSource
import com.ssafy.neegongnaegong.data.mapper.studies.StudiesApplicationsMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.studies.StudiesContentsMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.studies.StudiesFeedsMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.studies.StudiesMemberMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.studies.StudiesWeeklyRankingsMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.vote.VoteMapper.toCreateRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateNoticeRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesApplicationsMembersRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesListRequest
import com.ssafy.neegongnaegong.data.model.studies.request.PatchStudiesProfileImage
import com.ssafy.neegongnaegong.data.model.studies.request.UpdateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesWeeklyRankingsResponse
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesApplications
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesFeeds
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesPage
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesWeeklyRankings
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContents
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContentsReadStatus
import com.ssafy.neegongnaegong.domain.model.studies.StudiesMember
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.VoteInfo
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import com.ssafy.neegongnaegong.presentation.group.role.component.StudiesMemberRole
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class StudiesRepositoryImpl
    @Inject
    constructor(
        private val dataSource: NetworkStudiesDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : StudiesRepository {
        override suspend fun getStudies(): List<Studies> = TODO()

        override suspend fun createVote(
            studyGroupId: Long,
            voteInfo: VoteInfo,
        ): Flow<Unit> = dataSource.createVote(studyGroupId, voteInfo.toCreateRequest()).flowOn(ioDispatcher)

        override fun createNotice(
            studyGroupId: Long,
            title: String,
            content: String,
        ): Flow<Unit> =
            dataSource
                .createNotice(studyGroupId, CreateNoticeRequest(title, content))
                .flowOn(ioDispatcher)

        override suspend fun getStudiesList(
            cursorCreatedAt: String?,
            cursorId: Long?,
            size: Int,
        ): Flow<CursorStudiesPage> =
            withContext(ioDispatcher) {
                dataSource
                    .getStudiesList(
                        GetStudiesListRequest(
                            cursorCreatedAt = cursorCreatedAt,
                            cursorId = cursorId,
                            size = size,
                        ),
                    ).map { slice ->
                        CursorSliceStudiesListResponse(
                            content = slice.content,
                            cursorCreatedAt = slice.cursorCreatedAt,
                            hasNext = slice.hasNext,
                            cursorId = slice.cursorId,
                        ).toDomain()
                    }
            }

        override suspend fun createStudies(studyInfo: StudyInfo): Flow<Long> =
            withContext(ioDispatcher) {
                dataSource.createStudies(request = CreateStudiesRequest.fromDomain(studyInfo))
            }

        override fun changeStudiesProfileImage(
            studyGroupId: Long,
            profileImage: String,
        ): Flow<Unit> =
            dataSource
                .changeStudiesProfileImage(
                    studyGroupId = studyGroupId,
                    request = PatchStudiesProfileImage(profileImage),
                ).flowOn(context = ioDispatcher)

        override suspend fun updateStudies(
            studyGroupId: Long,
            studyInfo: StudyInfo,
        ): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.updateStudies(
                    studyGroupId = studyGroupId,
                    request = UpdateStudiesRequest.fromDomain(studyInfo),
                )
            }

        override suspend fun deleteStudies(studyGroupId: Long): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.deleteStudies(studyGroupId)
            }

        override suspend fun applyStudies(studyGroupId: Long): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.applyStudies(studyGroupId)
            }

        override suspend fun cancelApplicationsStudies(studyGroupId: Long): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.cancelApplicationsStudies(studyGroupId)
            }

        override suspend fun getStudiesMembers(studyGroupId: Long): Flow<List<StudiesMember>> =
            withContext(ioDispatcher) {
                dataSource
                    .getStudiesMembers(studyGroupId)
                    .map { dto -> dto.memberRespons.map { it.toDomain() } }
            }

        override suspend fun getStudiesApplications(
            studyGroupId: Long,
            cursorId: Long?,
            size: Int,
        ): Flow<CursorStudiesApplications> =
            withContext(ioDispatcher) {
                dataSource.getStudiesApplications(
                    request =
                        GetStudiesApplicationsMembersRequest(
                            studyGroupId = studyGroupId,
                            cursorId = cursorId,
                            size = size,
                        ),
                )
            }.map { it.toDomain() }

        override suspend fun patchApproveStudiesApplications(
            studyGroupId: Long,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.patchApproveStudiesApplications(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    notificationId = notificationId,
                )
            }

        override suspend fun patchRejectStudiesApplications(
            studyGroupId: Long,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.patchRejectStudiesApplications(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    notificationId = notificationId,
                )
            }

        override suspend fun changeRoleStudiesMember(
            studyGroupId: Long,
            userId: Long,
            changeRole: StudiesMemberRole,
        ): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.changeRoleStudiesMember(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    changeRole = changeRole.name,
                )
            }

        override suspend fun expelStudiesMember(
            studyGroupId: Long,
            userId: Long,
        ): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.expelStudiesMember(
                    studyGroupId = studyGroupId,
                    userId = userId,
                )
            }

        override fun getStudiesFeeds(
            studyGroupId: Long,
            cursorCreatedAt: LocalDateTime?,
            cursorId: Long?,
            size: Int,
        ): Flow<CursorStudiesFeeds> =
            dataSource
                .getStudiesFeeds(
                    studyGroupId = studyGroupId,
                    cursorCreatedAt = cursorCreatedAt,
                    cursorId = cursorId,
                    size = size,
                ).map { it.toDomain() }
                .flowOn(context = ioDispatcher)

        override fun getStudiesWeeklyRankings(
            studyGroupId: Long,
            cursorStudyTime: Long?,
            cursorUserId: Long?,
            firstPageRequestedAt: LocalDateTime?,
            size: Int,
        ): Flow<CursorStudiesWeeklyRankings> =
            dataSource
                .getStudiesWeeklyRankings(
                    studyGroupId = studyGroupId,
                    cursorStudyTime = cursorStudyTime,
                    cursorUserId = cursorUserId,
                    firstPageRequestedAt = firstPageRequestedAt,
                    size = size,
                ).map { getStudiesWeeklyRankingsResponse: GetStudiesWeeklyRankingsResponse -> getStudiesWeeklyRankingsResponse.toDomain() }
                .flowOn(context = ioDispatcher)

        override fun getStudiesLatestContents(studyGroupId: Long): Flow<StudiesLatestContents> =
            dataSource
                .getStudiesLatestContents(
                    studyGroupId = studyGroupId,
                ).map { it.toDomain() }
                .flowOn(context = ioDispatcher)

        override fun getStudiesLatestContentsReadStatus(studyGroupId: Long): Flow<StudiesLatestContentsReadStatus> =
            dataSource
                .getStudiesLatestContentsReadStatus(
                    studyGroupId = studyGroupId,
                ).map { it.toDomain() }
                .flowOn(context = ioDispatcher)

        override fun patchStudiesLatestContentsReadStatus(
            studyGroupId: Long,
            readNotice: Boolean?,
            readVote: Boolean?,
        ): Flow<Unit> =
            dataSource
                .patchStudiesLatestContentsReadStatus(
                    studyGroupId = studyGroupId,
                    readNotice = readNotice,
                    readVote = readVote,
                ).flowOn(context = ioDispatcher)
    }
