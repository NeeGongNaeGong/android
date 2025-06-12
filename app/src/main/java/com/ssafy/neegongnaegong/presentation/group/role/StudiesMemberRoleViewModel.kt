package com.ssafy.neegongnaegong.presentation.group.role

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.studies.ChangeRoleStudiesMemberUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.ExpelStudiesMemberUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesMembersUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.role.component.StudiesMemberRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudiesMemberRoleViewModel
    @Inject
    constructor(
        private val getStudiesMembersUseCase: GetStudiesMembersUseCase,
        private val changeRoleStudiesMemberUseCase: ChangeRoleStudiesMemberUseCase,
        private val expelStudiesMemberUseCase: ExpelStudiesMemberUseCase,
    ) : BaseViewModel<StudiesMemberRoleContract.Event, StudiesMemberRoleContract.State, StudiesMemberRoleContract.Effect>() {
        override fun createInitialState(): StudiesMemberRoleContract.State = StudiesMemberRoleContract.State(studyGroupId = -1)

        override fun handleEvent(event: StudiesMemberRoleContract.Event) {
            when (event) {
                is StudiesMemberRoleContract.Event.OnLoadStudiesMembers -> onLoadStudiesMembers(event.studyGroupId)
                is StudiesMemberRoleContract.Event.OnChangeMemberRole ->
                    changeRoleStudiesMember(
                        event.userId,
                        event.changeRole,
                    )

                is StudiesMemberRoleContract.Event.OnExpelMember -> expelStudiesMember(event.userId)

                is StudiesMemberRoleContract.Event.OnSelectedMember ->
                    setState { copy(selectedMember = event.selectedMember) }

                is StudiesMemberRoleContract.Event.OnChangeRoleDialogShow ->
                    setState { copy(isChangeRoleDialogShow = true) }

                is StudiesMemberRoleContract.Event.OnChangeRoleDialogCancel ->
                    setState { copy(isChangeRoleDialogShow = false) }

                is StudiesMemberRoleContract.Event.OnChangeRoleDialogConfirm -> {
                    setState { copy(isChangeRoleDialogShow = false) }
                    changeRoleStudiesMember(
                        event.userId,
                        event.changeRole,
                    )
                }

                is StudiesMemberRoleContract.Event.OnChangeRoleDialogDismiss -> {
                    setState { copy(isChangeRoleDialogShow = false) }
                }

                is StudiesMemberRoleContract.Event.OnExpelMemberDialogShow ->
                    setState { copy(isExpelMemberDialogShow = true) }

                is StudiesMemberRoleContract.Event.OnExpelMemberDialogCancel ->
                    setState { copy(isExpelMemberDialogShow = false) }

                is StudiesMemberRoleContract.Event.OnExpelMemberDialogConfirm -> {
                    setState { copy(isExpelMemberDialogShow = false) }
                    expelStudiesMember(event.userId)
                }

                is StudiesMemberRoleContract.Event.OnExpelMemberDialogDismiss ->
                    setState { copy(isExpelMemberDialogShow = false) }
            }
        }

        private fun onLoadStudiesMembers(studyGroupId: Long) {
            setState { copy(studyGroupId = studyGroupId) }
            viewModelScope.launch {
                getStudiesMembersUseCase(studyGroupId)
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        setState { copy(studiesMembers = result) }
                    }
            }
        }

        private fun changeRoleStudiesMember(
            userId: Long,
            changeRole: StudiesMemberRole,
        ) {
            viewModelScope.launch {
                changeRoleStudiesMemberUseCase(
                    studyGroupId = uiState.value.studyGroupId,
                    userId = userId,
                    changeRole = changeRole,
                ).withLoading {
                    setState { copy(isLoading = it) }
                }.safeCollect {
                    setState {
                        copy(
                            studiesMembers =
                                studiesMembers.map { member ->
                                    if (member.userId == userId) member.copy(groupRole = changeRole) else member
                                },
                        )
                    }
                }
            }
        }

        private fun expelStudiesMember(userId: Long) {
            viewModelScope.launch {
                expelStudiesMemberUseCase(
                    studyGroupId = uiState.value.studyGroupId,
                    userId = userId,
                ).withLoading {
                    setState { copy(isLoading = it) }
                }.safeCollect {
                    setState {
                        copy(
                            studiesMembers =
                                studiesMembers.filter { member ->
                                    member.userId != userId
                                },
                        )
                    }
                }
            }
        }
    }
