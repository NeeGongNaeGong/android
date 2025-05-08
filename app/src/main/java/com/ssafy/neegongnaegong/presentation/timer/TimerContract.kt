package com.ssafy.neegongnaegong.presentation.timer

import android.os.SystemClock
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import com.ssafy.neegongnaegong.presentation.timer.learning.LearningRecordWriteContract.Error

class TimerContract {
    sealed class Event : UiEvent {
        data object OnPauseClicked : Event()

        data object OnPlayClicked : Event()

        data object OnDismissDialog : Event()

        data object OnConfirmDialog : Event()

        data object OnCancelDialog : Event()

        data object OffScreen : Event()

        data object OnScreen : Event()

        data class OnFlip(
            val isBack: Boolean,
        ) : Event()
    }

    data class State(
        val isTimerScreen: Boolean = true,
        val isRunning: Boolean = false,
        val isLoading: Boolean = false,
        //api
        val learningRecord: LearningRecord = LearningRecord.default(),
        // timer
        val startTime: Long = SystemClock.elapsedRealtime(),
        val totalElapsedTime: Long = 0L,
        val isFirstTimer: Boolean = true,
        // dialog
        val isDialogShow: Boolean = true,
        val isPauseDialogVisible: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToWriteScreen : Effect()
    }

    sealed class Error : ErrorContext {
        data object CreateLearningRecordError : Error()

        data object TagOverSizeError : Error()
    }
}
