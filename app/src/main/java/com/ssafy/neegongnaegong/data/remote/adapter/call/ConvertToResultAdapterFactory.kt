package com.ssafy.neegongnaegong.data.remote.adapter.call

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ConvertToResultAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        /**
         * Call 객체인지 확인한다.
         */
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) {
            return null
        }

        /**
         * Call 객제로 부터 T type을 획득한다.
         */
        val upperBound = getParameterUpperBound(0, returnType)

        return if (upperBound is ParameterizedType && upperBound.rawType == Result::class.java) {
            val bodyType = getParameterUpperBound(0, upperBound)
            return ConvertToResultCallAdapter<Any>(bodyType, retrofit)
        } else {
            null
        }
    }
}
