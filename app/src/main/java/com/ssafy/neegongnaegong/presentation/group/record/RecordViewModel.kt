package com.ssafy.neegongnaegong.presentation.group.record

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetMemberStudyContentsUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetMemberStudyLogsByTagUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel
    @Inject
    constructor(
        getMemberStudyContentsUseCase: GetMemberStudyContentsUseCase,
        getMemberStudyLogsByTagUseCase: GetMemberStudyLogsByTagUseCase,
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<RecordContract.Event, RecordContract.State, RecordContract.Effect>() {
        override fun createInitialState(): RecordContract.State = RecordContract.State(persistentListOf())

        override fun handleEvent(event: RecordContract.Event) {
            when (event) {
                // Record Screen의 인자가 제대로 들어오지 않은 경우
                RecordContract.Event.InvalidAccess -> {
                    setEffect { RecordContract.Effect.NavigateToBackStack }
                }
            }
        }

        val studyLogFlow: Flow<PagingData<StudyContentInfo>>

        init {
            val (studyGroupId, userId) =
                with(savedStateHandle.toRoute<AppNavigation.Screen.Studies.Record>()) {
                    Pair(this.studyGroupId, memberId)
                }

            studyLogFlow =
                getMemberStudyContentsUseCase(StudyMemberInfo(studyGroupId, userId)).cachedIn(
                    viewModelScope,
                )

            viewModelScope.launch {
                getMemberStudyLogsByTagUseCase(StudyMemberInfo(studyGroupId, userId)).safeCollect {
                    setState { copy(studyLogsByTag = it.toPersistentList()) }
                }
            }
        }
    }
