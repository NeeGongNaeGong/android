package com.ssafy.neegongnaegong.presentation.component.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager

data class NeeGongNaeGongSnackbarVisuals(
    override val message: String,
    val type: SnackbarManager.Type,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals