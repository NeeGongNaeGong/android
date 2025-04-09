package com.ssafy.neegongnaegong.presentation.calendar.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MaterialTheme.textFieldColors(): TextFieldColors = TextFieldDefaults.colors(
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    focusedContainerColor = colorScheme.primaryContainer,
    unfocusedContainerColor = colorScheme.primaryContainer,
    disabledContainerColor = colorScheme.primaryContainer,
    focusedTextColor = colorScheme.onBackground,
    unfocusedTextColor = colorScheme.onBackground,
    disabledTextColor = colorScheme.onBackground,
    focusedPlaceholderColor = colorScheme.onBackground,
    unfocusedPlaceholderColor = colorScheme.onBackground,
    disabledPlaceholderColor = colorScheme.onBackground,
    focusedPrefixColor = colorScheme.onBackground,
    unfocusedPrefixColor = colorScheme.onBackground,
    disabledPrefixColor = colorScheme.onBackground
)