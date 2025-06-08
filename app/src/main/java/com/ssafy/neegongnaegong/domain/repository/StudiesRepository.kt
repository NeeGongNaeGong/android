package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesApplications
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesPage
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudiesMember
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.VoteInfo
import com.ssafy.neegongnaegong.presentation.group.role.component.StudiesMemberRole
import kotlinx.coroutines.flow.Flow

interface StudiesRepository {
    suspend fun getStudies(): List<Studies>

    suspend fun createVote(
        studyId: Int,
        voteInfo: VoteInfo,
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
}
