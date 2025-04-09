package com.ssafy.neegongnaegong.data.remote.adapter.call

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.domain.exception.ApiException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * 서버로부터 T type의 응답을 받으면 Result<T> type으로 변환한다.
 */
class ConvertToResultCall<T> (private val delegate: Call<T>, private val retrofit: Retrofit) : Call<Result<T>> {
    /**
     * 같은 요청을 다시 보낼때 호출되는 함수이다.
     */
    override fun clone(): Call<Result<T>> = ConvertToResultCall(delegate.clone(), retrofit)

    /**
     * suspend 아닐시 실행되는 함수이다.
     * 사용하지 않기 때문에 최소한의 작업만 진행한다.
     */
    override fun execute(): Response<Result<T>> = Response.success(Result.success(delegate.execute().body()!!))

    /**
     * suspend 일시 실행되는 함수이다.
     * onResponse는 정상적으로 데이터를 받아왔을 때 실행된다.
     * onFailure는 exception이 발생한 경우(timeout error, network error, interceptor error 등) 실행된다.
     */
    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(object : Callback<T> {
            /**
             * Response<T> 타입을 Response<Result<T>>로 변환한다.
             */
            fun <T> Response<T>.getResult(retrofit: Retrofit): Response<Result<T>> {
                return if (isSuccessful) {
                    /**
                     * 성공시에도 body가 null 일수 있기 때문에 체크하고 null이면 failure를 반환한다.
                     */
                    body()?.let { body ->
                        Response.success(
                            code(),
                            Result.success(body)
                        )
                    } ?: Response.success(
                        code(),
                        Result.failure(ApiException.UnknownException())
                    )
                } else {
                    /**
                     * 실패시에도 서버가 errorBody를 보내주지 않을 수 있기 때문에 null 체크를 한다.
                     * 현재 서버가 에러시 ApiResponse<Unit> 타입으로 전송하기 때문에 타입을 맞춰준다.
                     * 만양 errorBody가 null이면 UnknownException을 반환한다.
                     */
                    errorBody()?.let { error ->
                        val errorBody = retrofit.responseBodyConverter<ApiResponse<Unit>>(
                            ApiResponse::class.java,
                            ApiResponse::class.java.annotations
                        ).convert(error)

                        Response.success(
                            code(),
                            Result.failure(ApiException.fromCode(code(), errorBody?.message))
                        )
                    } ?: Response.success(
                        code(),
                        Result.failure(ApiException.UnknownException())
                    )
                }
            }

            /**
             * 서버와 통신이 정상적으로 이루어진 경우 실행된다.
             * 400, 500대 응답도 onResponse가 실행된다.
             * token 만료의 경우 AuthInterceptor에서 exception을 발생시키므로 onFailure가 실행된다.
             */
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onResponse(
                    this@ConvertToResultCall,
                    response.getResult(retrofit)
                )
            }

            /**
             * exception이 발생한 경우(timeout error, network error, interceptor error 등) 실행된다.
             * exception은 ApiException.fromCommonException을 통해 ApiException으로 치환된다.
             * AuthException의 경우 그대로 사용된다.
             */
            override fun onFailure(p0: Call<T>, error: Throwable) {
                callback.onResponse(
                    this@ConvertToResultCall,
                    Response.success(Result.failure(ApiException.fromCommonException(error)))
                )
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
