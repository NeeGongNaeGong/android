package com.ssafy.neegongnaegong.presentation.group

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.StudyMember
import com.ssafy.neegongnaegong.domain.usecase.GetStudiesUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudiesViewModel
    @Inject
    constructor(
        private val getStudiesUseCase: GetStudiesUseCase,
    ) : BaseViewModel<StudiesContract.Event, StudiesContract.State, StudiesContract.Effect>() {
        override fun createInitialState(): StudiesContract.State =
            StudiesContract.State(
                StudiesContract.StudiesState.Idle,
            )

        override fun handleEvent(event: StudiesContract.Event) {
            when (event) {
                is StudiesContract.Event.LoadGroups -> fetchGroups()
                is StudiesContract.Event.StudiesClicked -> {
                    setEffect { StudiesContract.Effect.NavigateToGroupDetail(event.studiesId) }
                }
            }
        }

        private fun fetchGroups() {
            viewModelScope.launch {
                setState { copy(studiesState = StudiesContract.StudiesState.Loading) }

                runCatching {
                    // TODO: 스터디 그룹 레포 적용
                    getDummyStudies()
                }.onSuccess { groups ->
                    setState { copy(studiesState = StudiesContract.StudiesState.Success(groups)) }
                }.onFailure {
                    setState { copy(studiesState = StudiesContract.StudiesState.Error("그룹 정보를 불러오지 못했습니다.")) }
                    showErrorMessage("그룹 정보를 불러오지 못했습니다.")
                }
            }
        }

        // Temporary dummy data
        private fun getDummyStudies(): List<Studies> =
            listOf(
                Studies(
                    id = 9337,
                    leader =
                        StudyMember(
                            id = 2220,
                            name = "Alfredo Humphrey",
                        ),
                    currentMembers = 6628,
                    createdDate = "evertitur",
                    studyInfo =
                        StudyInfo(
                            name = "Jermaine Ochoa",
                            maxMembers = 1969,
                            description = "cu",
                            profileImg = null,
                            isPublic = false,
                            targetStudyTime = 9500,
                            category = null,
                            tags = listOf(),
                        ),
                ),
            )
    }
