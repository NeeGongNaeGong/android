package com.ssafy.neegongnaegong.data.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <reified T> apiFlow(crossinline call: suspend () -> Result<ApiResponse<T>>): Flow<T> = flow {
    emit(call().getOrThrow().data)
}
