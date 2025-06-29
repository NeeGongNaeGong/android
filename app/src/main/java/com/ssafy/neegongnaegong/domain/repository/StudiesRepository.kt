package com.ssafy.neegongnaegong.domain.repository

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
import com.ssafy.neegongnaegong.presentation.group.role.component.StudiesMemberRole
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface StudiesRepository {
    suspend fun getStudies(): List<Studies>

    suspend fun createVote(
        studyGroupId: Long,
        voteInfo: VoteInfo,
    ): Flow<Unit>

    fun createNotice(
        studyGroupId: Long,
        title: String,
        content: String,
    ): Flow<Unit>

    suspend fun getStudiesList(
        cursorCreatedAt: String?,
        cursorId: Long?,
        size: Int,
    ): Flow<CursorStudiesPage>

    suspend fun createStudies(studyInfo: StudyInfo): Flow<Unit>

    suspend fun getStudiesDetail(studyGroupId: Long): Flow<Studies>

    suspend fun updateStudies(
        studyGroupId: Long,
        studyInfo: StudyInfo,
    ): Flow<Unit>

    suspend fun deleteStudies(studyGroupId: Long): Flow<Unit>

    suspend fun applyStudies(studyGroupId: Long): Flow<Unit>

    suspend fun cancelApplicationsStudies(studyGroupId: Long): Flow<Unit>

    suspend fun getStudiesMembers(studyGroupId: Long): Flow<List<StudiesMember>>

    suspend fun getStudiesApplications(
        studyGroupId: Long,
        cursorId: Long?,
        size: Int,
    ): Flow<CursorStudiesApplications>

    suspend fun patchApproveStudiesApplications(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?,
    ): Flow<Unit>

    suspend fun patchRejectStudiesApplications(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?,
    ): Flow<Unit>

    suspend fun changeRoleStudiesMember(
        studyGroupId: Long,
        userId: Long,
        changeRole: StudiesMemberRole,
    ): Flow<Unit>

    suspend fun expelStudiesMember(
        studyGroupId: Long,
        userId: Long,
    ): Flow<Unit>

    fun getStudiesFeeds(
        studyGroupId: Long,
        cursorCreatedAt: LocalDateTime?,
        cursorId: Long?,
        size: Int,
    ): Flow<CursorStudiesFeeds>

    fun getStudiesWeeklyRankings(
        studyGroupId: Long,
        cursorStudyTime: Long?,
        cursorUserId: Long?,
        firstPageRequestedAt: LocalDateTime?,
        size: Int,
    ): Flow<CursorStudiesWeeklyRankings>

    fun getStudiesLatestContents(studyGroupId: Long): Flow<StudiesLatestContents>

    fun getStudiesLatestContentsReadStatus(studyGroupId: Long): Flow<StudiesLatestContentsReadStatus>
}
