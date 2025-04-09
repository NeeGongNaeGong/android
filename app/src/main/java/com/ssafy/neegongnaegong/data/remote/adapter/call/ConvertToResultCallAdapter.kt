package com.ssafy.neegongnaegong.data.remote.adapter.call

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * CallAdpater는 retrofit에서 Call 객체에 대한 생성을 제어할때 사용하는 class이다.
 * Call은 retrofit의 실제 HTTP 요청을 표현하는 객체이다.
 */
class ConvertToResultCallAdapter<T>(
    private val responseType: Type,
    private val retrofit: Retrofit
) : CallAdapter<T, Call<Result<T>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Call<Result<T>> = ConvertToResultCall(call, retrofit)
}
