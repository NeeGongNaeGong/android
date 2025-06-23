package com.ssafy.neegongnaegong.presentation.common

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.compositionLocalOf

val LocalDrawerState =
    compositionLocalOf<DrawerState> {
        error("DrawerState not provided")
    }
