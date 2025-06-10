package com.ssafy.neegongnaegong.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope

@Composable
fun LaunchedEffectAfterFirst(
    vararg keys: Any?,
    block: suspend CoroutineScope.() -> Unit
) {
    var hasLaunched by remember { mutableStateOf(false) }

    LaunchedEffect(*keys) {
        if (hasLaunched) {
            block()
        } else {
            hasLaunched = true
        }
    }
}
