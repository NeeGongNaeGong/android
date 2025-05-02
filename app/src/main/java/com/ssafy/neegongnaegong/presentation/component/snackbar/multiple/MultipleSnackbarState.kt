package com.ssafy.neegongnaegong.presentation.component.snackbar.multiple

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.ssafy.neegongnaegong.presentation.component.snackbar.NeeGongNaeGongSnackbarVisuals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID


@Composable
fun rememberMultipleSnackbarState(durationMillis: Long = 4000L): MultipleSnackbarState {
    return remember { MultipleSnackbarState(durationMillis) }
}

@Stable
class MultipleSnackbarState(private val durationMillis: Long) {
    private val _snackbars = mutableStateListOf<SnackbarEntry>()
    val snackbars: List<SnackbarEntry> = _snackbars

    // 코루틴 스코프 정의
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Stable
    data class SnackbarEntry(
        val id: String,
        val visuals: NeeGongNaeGongSnackbarVisuals
    )

    fun showSnackbar(
        visuals: NeeGongNaeGongSnackbarVisuals,
    ): String {
        val id = UUID.randomUUID().toString()
        _snackbars.add(0, SnackbarEntry(id, visuals)) // 새 Snackbar를 아래에 추가

        // 독립적인 코루틴에서 타이머 시작
        scope.launch {
            delay(durationMillis)
            dismissSnackbar(id)
        }

        return id
    }

    fun dismissSnackbar(id: String) {
        _snackbars.removeAll { it.id == id }
    }

    // 클래스가 더 이상 사용되지 않을 때 코루틴 스코프를 정리
    fun clear() {
        scope.cancel()
        _snackbars.clear()
    }
}

