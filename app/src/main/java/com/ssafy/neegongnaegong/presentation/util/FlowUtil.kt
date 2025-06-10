package com.ssafy.neegongnaegong.presentation.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest

object FlowUtil {
    @OptIn(ExperimentalCoroutinesApi::class)
    inline fun <T, R> Flow<T>.safeFlatMapLatest(
        crossinline transform: suspend (value: T) -> Flow<R>,
        crossinline catch: (throwable: Throwable) -> Unit
    ) = flatMapLatest { value: T ->
        transform(value).catch { throwable: Throwable ->
            catch(throwable)
        }
    }
}
