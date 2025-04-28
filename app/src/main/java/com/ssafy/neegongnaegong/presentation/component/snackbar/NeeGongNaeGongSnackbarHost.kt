package com.ssafy.neegongnaegong.presentation.component.snackbar

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.neegongnaegong.presentation.base.SnackbarViewModel
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager

@Composable
fun NeeGongNaeGongSnackbarHost() {
    val snackbarViewModel: SnackbarViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        snackbarViewModel.message.collect {
            val (message, type) = it
            val visuals = NeeGongNaeGongSnackbarVisuals(message, type)
            snackbarHostState.showSnackbar(visuals)
        }
    }

    SnackbarHost(hostState = snackbarHostState) {
        val visuals = it.visuals as? NeeGongNaeGongSnackbarVisuals ?: return@SnackbarHost
        NeeGongNaeGongSnackbarWithVisuals(
            modifier = Modifier.dismissWithDragGestures {
                snackbarHostState.currentSnackbarData?.dismiss()
            },
            visuals = visuals,
        )
    }
}

@Composable
fun NeeGongNaeGongSnackbarWithVisuals(
    modifier: Modifier = Modifier,
    visuals: NeeGongNaeGongSnackbarVisuals
) {
    val (message, type) = visuals

    val backgroundColor = when (type) {
        SnackbarManager.Type.None -> NeeGongNaeGongTheme.colorScheme.gray1
        SnackbarManager.Type.Success -> NeeGongNaeGongTheme.colorScheme.mint
        SnackbarManager.Type.Warning -> NeeGongNaeGongTheme.colorScheme.lightGreen
        SnackbarManager.Type.Error -> NeeGongNaeGongTheme.colorScheme.peach
    }

    val prefixEmoji = when (type) {
        SnackbarManager.Type.None -> null
        SnackbarManager.Type.Success -> "✓"
        SnackbarManager.Type.Warning -> "⚠"
        SnackbarManager.Type.Error -> "ⓘ"
    }

    NeeGongNaeGongSnackbar(
        modifier = modifier,
        message = message,
        backgroundColor = backgroundColor,
        prefix = { EmojiText(prefixEmoji) }
    )
}


@NeeGongNaeGongPreviews
@Composable
fun NeeGongNaeGongSnackbarWithVisualsPreview_None() {
    NeeGongNaeGongTheme {
        NeeGongNaeGongSnackbarWithVisuals(
            visuals = NeeGongNaeGongSnackbarVisuals(
                message = "일반 메시지",
                type = SnackbarManager.Type.None
            )
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun NeeGongNaeGongSnackbarWithVisualsPreview_Success() {
    NeeGongNaeGongTheme {
        NeeGongNaeGongSnackbarWithVisuals(
            visuals = NeeGongNaeGongSnackbarVisuals(
                message = "성공 메시지",
                type = SnackbarManager.Type.Success
            )
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun NeeGongNaeGongSnackbarWithVisualsPreview_Warning() {
    NeeGongNaeGongTheme {
        NeeGongNaeGongSnackbarWithVisuals(
            visuals = NeeGongNaeGongSnackbarVisuals(
                message = "경고 메시지",
                type = SnackbarManager.Type.Warning
            )
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun NeeGongNaeGongSnackbarWithVisualsPreview_Error() {
    NeeGongNaeGongTheme {
        NeeGongNaeGongSnackbarWithVisuals(
            visuals = NeeGongNaeGongSnackbarVisuals(
                message = "에러 메시지",
                type = SnackbarManager.Type.Error
            )
        )
    }
}
