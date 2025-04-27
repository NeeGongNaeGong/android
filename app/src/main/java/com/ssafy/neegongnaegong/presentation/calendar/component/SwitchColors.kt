package com.ssafy.neegongnaegong.presentation.calendar.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable

@Composable
fun MaterialTheme.switchColors() = SwitchDefaults.colors().copy(
    checkedThumbColor = colorScheme.primary,
    checkedTrackColor = colorScheme.primary.copy(alpha = 0.5f),
    checkedBorderColor = colorScheme.primary,
    checkedIconColor = colorScheme.onPrimary,
    uncheckedThumbColor = colorScheme.onBackground,
    uncheckedTrackColor = colorScheme.onBackground.copy(alpha = 0.5f),
    uncheckedBorderColor = colorScheme.onBackground,
    uncheckedIconColor = colorScheme.onBackground,
    disabledCheckedThumbColor = colorScheme.primary,
    disabledCheckedTrackColor = colorScheme.primary.copy(alpha = 0.5f),
    disabledCheckedBorderColor = colorScheme.primary,
    disabledCheckedIconColor = colorScheme.onPrimary,
    disabledUncheckedThumbColor = colorScheme.onBackground,
    disabledUncheckedTrackColor = colorScheme.onBackground.copy(alpha = 0.5f),
    disabledUncheckedBorderColor = colorScheme.onBackground,
    disabledUncheckedIconColor = colorScheme.onBackground,
)