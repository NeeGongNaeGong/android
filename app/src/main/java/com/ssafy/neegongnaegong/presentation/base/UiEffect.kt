package com.ssafy.neegongnaegong.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

interface UiEffect

@Composable
fun <E : UiEffect> CollectSideEffects(
    effectFlow: Flow<E>,
    onEffect: suspend (E) -> Unit,
) = LaunchedEffect(key1 = effectFlow) {
    effectFlow.collect { effect: E ->
        onEffect(effect)
    }
}
