package com.ssafy.neegongnaegong.presentation.group.role

import com.ssafy.neegongnaegong.domain.model.studies.StudiesMember
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import com.ssafy.neegongnaegong.presentation.group.role.component.StudiesMemberRole

class StudiesMemberRoleContract {
    sealed interface Event : UiEvent {
        data class OnLoadStudiesMembers(
            val studyGroupId: Long,
        ) : Event

        data class OnChangeMemberRole(
            val userId: Long,
            val changeRole: StudiesMemberRole,
        ) : Event

        data class OnExpelMember(
            val userId: Long,
        ) : Event

        data class OnSelectedMember(
            val selectedMember: StudiesMember,
        ) : Event

        data object OnChangeRoleDialogShow : Event

        data object OnChangeRoleDialogCancel : Event

        data class OnChangeRoleDialogConfirm(
            val userId: Long,
            val changeRole: StudiesMemberRole,
        ) : Event

        data object OnChangeRoleDialogDismiss : Event

        data object OnExpelMemberDialogShow : Event

        data object OnExpelMemberDialogCancel : Event

        data class OnExpelMemberDialogConfirm(
            val userId: Long,
        ) : Event

        data object OnExpelMemberDialogDismiss : Event
    }

    data class State(
        val isLoading: Boolean = false,
        val studyGroupId: Long,
        val studiesMembers: List<StudiesMember> = emptyList(),
        val selectedMember: StudiesMember? = null,
        val isChangeRoleDialogShow: Boolean = false,
        val isExpelMemberDialogShow: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect

    sealed class Error : ErrorContext
}
