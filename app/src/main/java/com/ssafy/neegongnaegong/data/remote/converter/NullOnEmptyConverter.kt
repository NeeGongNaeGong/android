package com.ssafy.neegongnaegong.data.remote.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * 서버에서 Null을 던졌더라도, 제대로 받지 못하는 것을 해결하기 위한 Converter
 * length를 통해 비어있는지 확인하여 비어있다면 Null을 반환하도록 한다.
 */
class NullOnEmptyConverter: Converter.Factory() {
    fun converterFactory() = this

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ) = object : Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(
            converterFactory(),
            type,
            annotations
        )

        override fun convert(value: ResponseBody) =
            if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value)
            else null
    }
}
