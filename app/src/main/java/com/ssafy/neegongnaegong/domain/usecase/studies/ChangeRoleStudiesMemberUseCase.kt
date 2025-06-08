package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.presentation.group.role.component.StudiesMemberRole

class ChangeRoleStudiesMemberUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(
        studyGroupId: Long,
        userId: Long,
        changeRole: StudiesMemberRole,
    ) = studiesRepository.changeRoleStudiesMember(studyGroupId = studyGroupId, userId = userId, changeRole = changeRole)
}
