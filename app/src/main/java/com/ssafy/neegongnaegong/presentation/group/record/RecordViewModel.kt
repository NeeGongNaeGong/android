package com.ssafy.neegongnaegong.presentation.group.record

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetMemberStudyContentsUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetMemberStudyLogsByTagUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel
@Inject
constructor(
    getMemberStudyContentsUseCase: GetMemberStudyContentsUseCase,
    getMemberStudyLogsByTagUseCase: GetMemberStudyLogsByTagUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RecordContract.Event, RecordContract.State, RecordContract.Effect>() {
    override fun createInitialState(): RecordContract.State =
        RecordContract.State(persistentListOf())

    override fun handleEvent(event: RecordContract.Event) {
        when (event) {
            // Record Screen의 인자가 제대로 들어오지 않은 경우
            RecordContract.Event.InvalidAccess -> {
                setEffect { RecordContract.Effect.NavigateToBackStack }
            }
        }
    }

    private val groupId: Long? = savedStateHandle["groupId"]
    private val userId: Long? = savedStateHandle["memberId"]
    val studyLogFlow: Flow<PagingData<StudyContentInfo>>

    init {
        if (groupId != null && userId != null) {
            studyLogFlow = getMemberStudyContentsUseCase(StudyMemberInfo(groupId, userId)).cachedIn(
                viewModelScope
            )
            viewModelScope.launch {
                getMemberStudyLogsByTagUseCase(StudyMemberInfo(groupId, userId)).safeCollect {
                    setState { copy(studyLogsByTag = it.toPersistentList()) }
                }
            }
        } else {
            // studyLogFlow를 nullable하게 처리하고 싶지 않다보니 에러로 쓰지 않을 것임에도 초기화를 진행하게 되네요
            studyLogFlow = flow {}
            showErrorMessage("잘못된 경로입니다")
            setEvent(RecordContract.Event.InvalidAccess)
        }
    }


}
