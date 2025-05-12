package com.ssafy.neegongnaegong.presentation.group.record

import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import kotlinx.collections.immutable.PersistentList

class RecordContract {
    sealed interface Event : UiEvent {
        data object InvalidAccess : Event
    }

    data class State(
        val studyLogsByTag:  PersistentList<StudyLogByTagInfo>,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToBackStack : Effect()
    }
}
