package com.ssafy.neegongnaegong.presentation.group

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.studies.Studies
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
                    setEffect { StudiesContract.Effect.ShowToast("그룹 정보를 불러오지 못했습니다.") }
                }
            }
        }

        // Temporary dummy data
        private fun getDummyStudies(): List<Studies> =
            listOf(
                Studies(
                    id = 1L,
                    category = "대학생",
                    title = "개발, 코딩(프론트, 백엔드 등) 취준방",
                    goalTime = "목표 3시간",
                    memberInfo = "인원 3/20명",
                    leader = "그룹장 박준식",
                    startInfo = "시작일 2일 전",
                    description = "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다. 매일 함께 공부해요! 질문과 答案을 자유롭게 나누며 함께 성장해 나가요.",
                ),
                Studies(
                    id = 2L,
                    category = "대학생",
                    title = "개발, 코딩(프론트, 백엔드 등) 취준방",
                    goalTime = "목표 3시간",
                    memberInfo = "인원 3/20명",
                    leader = "그룹장 박준식",
                    startInfo = "시작일 2일 전",
                    description = "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다. 매일 함께 공부해요! 질문과 答案을 자유롭게 나누며 함께 성장해 나가요. ~~~~~ @(@)@! ...",
                ),
                Studies(
                    id = 3L,
                    category = "대학생",
                    title = "개발, 코딩(프론트, 백엔드 등) 취준방",
                    goalTime = "목표 3시간",
                    memberInfo = "인원 3/20명",
                    leader = "그룹장 박준식",
                    startInfo = "시작일 2일 전",
                    description = "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다.",
                ),
                Studies(
                    id = 4L,
                    category = "대학생",
                    title = "개발, 코딩(프론트, 백엔드 등) 취준방",
                    goalTime = "목표 3시간",
                    memberInfo = "인원 3/20명",
                    leader = "그룹장 박준식",
                    startInfo = "시작일 2일 전",
                    description = "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다.",
                ),
            )
    }
