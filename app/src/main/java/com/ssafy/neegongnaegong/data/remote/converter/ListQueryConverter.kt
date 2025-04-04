package com.ssafy.neegongnaegong.data.remote.converter

import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ListQueryConverter : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        if (type is ParameterizedType && (type.rawType == List::class.java)) {
            return Converter<List<*>, String> { list ->
                "[${list.joinToString(", ")}]"
            }
        }
        return null
    }
}
