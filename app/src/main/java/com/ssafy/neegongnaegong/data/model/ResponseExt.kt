package com.ssafy.neegongnaegong.data.model

import com.ssafy.neegongnaegong.domain.exception.ApiException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response

fun <T> ApiResult<T>.toFlow(): Flow<T> = flow {
    when (this@toFlow) {
        is ApiResult.Success -> emit(data)
        is ApiResult.Error -> throw exception
    }
}

inline fun <reified T> safeApiCall(call: () -> Response<ApiResponse<T>>): ApiResult<T> {
    return runCatching { call() }
        .fold(
            onSuccess = { it.toApiResult() },
            onFailure = {
                ApiResult.Error(it as? ApiException ?: ApiException.UnknownException(it.message))
            }
        )
}

inline fun <reified T> Response<ApiResponse<T>>.toApiResult(): ApiResult<T> {
    body()?.let {
        if (isSuccessful) return ApiResult.Success(it.data)
        else return ApiResult.Error(ApiException.fromCode(code(), it.message))
    }

    errorBody()?.let {
        val errorMessage = it.charStream().readLines().joinToString()
        val message = runCatching { JSONObject(errorMessage).getString("message") }.fold(
            onSuccess = { message -> message },
            onFailure = { "" }
        )

        if (message.isNotBlank()) return ApiResult.Error(ApiException.fromCode(code(), message))
        else return ApiResult.Error(ApiException.fromCode(code()))
    }

    return if (isSuccessful) {
        if (T::class == Unit::class) ApiResult.Success(Unit as T)
        else ApiResult.Error(ApiException.UnknownException())
    } else ApiResult.Error(ApiException.fromCode(code()))
}
