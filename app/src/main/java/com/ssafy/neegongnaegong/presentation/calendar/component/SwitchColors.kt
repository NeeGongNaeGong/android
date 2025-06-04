package com.ssafy.neegongnaegong.presentation.calendar.component

import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun NeeGongNaeGongTheme.switchColors() = SwitchDefaults.colors().copy(
    checkedThumbColor = colorScheme.blue,
    checkedTrackColor = colorScheme.blue.copy(alpha = 0.5f),
    checkedBorderColor = colorScheme.blue,
    checkedIconColor = colorScheme.primaryText,
    uncheckedThumbColor = colorScheme.primaryText,
    uncheckedTrackColor = colorScheme.primaryText.copy(alpha = 0.5f),
    uncheckedBorderColor = colorScheme.primaryText,
    uncheckedIconColor = colorScheme.primaryText,
    disabledCheckedThumbColor = colorScheme.blue,
    disabledCheckedTrackColor = colorScheme.blue.copy(alpha = 0.5f),
    disabledCheckedBorderColor = colorScheme.blue,
    disabledCheckedIconColor = colorScheme.primaryText,
    disabledUncheckedThumbColor = colorScheme.primaryText,
    disabledUncheckedTrackColor = colorScheme.primaryText.copy(alpha = 0.5f),
    disabledUncheckedBorderColor = colorScheme.primaryText,
    disabledUncheckedIconColor = colorScheme.primaryText,
)