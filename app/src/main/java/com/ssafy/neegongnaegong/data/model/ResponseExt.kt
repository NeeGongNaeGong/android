package com.ssafy.neegongnaegong.data.model

import com.ssafy.neegongnaegong.data.model.calendar.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response

const val ERROR = "에러 발생"

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
            onFailure = { error -> ApiResult.Error(RuntimeException(error.message)) }
        )
}

inline fun <reified T> Response<ApiResponse<T>>.toApiResult(): ApiResult<T> {

    body()?.let {
        if (isSuccessful) return ApiResult.Success(it.data)
        else return ApiResult.Error(RuntimeException(ERROR))
    }

    errorBody()?.let {
        val errorMessage = it.charStream().readLines().joinToString()
        val json = JSONObject(errorMessage)
        val message = kotlin.runCatching { json.getString("message") }
            .fold(
                onSuccess = { message -> message },
                onFailure = { ERROR }
            )

        if (message.isNotBlank()) return ApiResult.Error(RuntimeException(message))
        else return ApiResult.Error(RuntimeException(ERROR))
    }

    return if (isSuccessful) {
        if (T::class == Unit::class) ApiResult.Success(Unit as T)
        else ApiResult.Error(RuntimeException(ERROR))
    } else ApiResult.Error(RuntimeException(ERROR))

}