package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.flow.Flow

class PatchApproveStudiesApplicationsUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?,
    ): Flow<Unit> =
        studiesRepository.patchApproveStudiesApplications(
            studyGroupId = studyGroupId,
            userId = userId,
            notificationId = notificationId,
        )
}
