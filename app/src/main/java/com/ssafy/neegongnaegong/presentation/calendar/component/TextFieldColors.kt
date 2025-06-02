package com.ssafy.neegongnaegong.presentation.calendar.component

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun NeeGongNaeGongTheme.textFieldColors(): TextFieldColors = TextFieldDefaults.colors(
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    focusedTextColor = colorScheme.primaryText,
    unfocusedTextColor = colorScheme.primaryText,
    disabledTextColor = colorScheme.primaryText,
    focusedPlaceholderColor = colorScheme.primaryText,
    unfocusedPlaceholderColor = colorScheme.primaryText,
    disabledPlaceholderColor = colorScheme.primaryText,
    focusedPrefixColor = colorScheme.primaryText,
    unfocusedPrefixColor = colorScheme.primaryText,
    disabledPrefixColor = colorScheme.primaryText
)