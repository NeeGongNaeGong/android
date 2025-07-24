package com.ssafy.neegongnaegong.presentation.group.join

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesApplicationsUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.PatchApproveStudiesApplicationsUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.PatchRejectStudiesApplicationsUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.join.component.StudiesJoinApplicationStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudiesWaitingToJoinViewModel
    @Inject
    constructor(
        private val getStudiesApplicationsUseCase: GetStudiesApplicationsUseCase,
        private val patchApproveStudiesApplicationsUseCase: PatchApproveStudiesApplicationsUseCase,
        private val patchRejectStudiesApplicationsUseCase: PatchRejectStudiesApplicationsUseCase,
    ) : BaseViewModel<StudiesWaitingToJoinContract.Event, StudiesWaitingToJoinContract.State, StudiesWaitingToJoinContract.Effect>() {
        override fun createInitialState(): StudiesWaitingToJoinContract.State = StudiesWaitingToJoinContract.State(studyGroupId = -1)

        override fun handleEvent(event: StudiesWaitingToJoinContract.Event) {
            when (event) {
                is StudiesWaitingToJoinContract.Event.OnLoadStudiesApplications ->
                    onLoadStudiesApplications(event.studyGroupId)

                is StudiesWaitingToJoinContract.Event.OnApproval ->
                    approveJoin(event.userId)

                is StudiesWaitingToJoinContract.Event.OnReject ->
                    rejectJoin(event.userId)
            }
        }

        private fun onLoadStudiesApplications(studyGroupId: Long) {
            setState { copy(studyGroupId = studyGroupId) }
            if (uiState.value.hasNext.not() || uiState.value.isLoading) { // 더 이상 불러올 데이터가 없으면 리턴
                return
            }

            viewModelScope.launch {
                getStudiesApplicationsUseCase(
                    studyGroupId = studyGroupId,
                    cursorId = uiState.value.cursorId,
                    size = 10,
                ).withLoading {
                    setState { copy(isLoading = it) }
                }.safeCollect { result ->
                    setState {
                        copy(
                            applicationsList = applicationsList + result.content,
                            hasNext = result.hasNext,
                            cursorId = result.nextCursor.cursorId,
                        )
                    }
                }
            }
        }

        private fun approveJoin(userId: Long) {
            viewModelScope.launch {
                patchApproveStudiesApplicationsUseCase(
                    studyGroupId = uiState.value.studyGroupId,
                    userId = userId,
                    notificationId = null,
                ).withLoading {
                    setState { copy(isLoading = it) }
                }.safeCollect {
                    setState {
                        copy(
                            applicationsList =
                                applicationsList.map { member ->
                                    if (member.userId == userId) {
                                        member.copy(status = StudiesJoinApplicationStatus.APPROVED)
                                    } else {
                                        member
                                    }
                                },
                        )
                    }
                }
            }
        }

        private fun rejectJoin(userId: Long) {
            viewModelScope.launch {
                patchRejectStudiesApplicationsUseCase(
                    studyGroupId = uiState.value.studyGroupId,
                    userId = userId,
                    notificationId = null,
                ).withLoading {
                    setState { copy(isLoading = it) }
                }.safeCollect {
                    setState {
                        copy(
                            applicationsList =
                                applicationsList.map { member ->
                                    if (member.userId == userId) {
                                        member.copy(status = StudiesJoinApplicationStatus.REJECTED)
                                    } else {
                                        member
                                    }
                                },
                        )
                    }
                }
            }
        }
    }
