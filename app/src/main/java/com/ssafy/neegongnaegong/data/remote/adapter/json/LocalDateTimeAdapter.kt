package com.ssafy.neegongnaegong.data.remote.adapter.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateTimeAdapter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    override fun serialize(
        src: LocalDateTime,
        typeOfSrc: Type,
        context: JsonSerializationContext,
    ): JsonElement {
        return JsonPrimitive(src.format(formatter))
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): LocalDateTime {
        val formatter =
            listOf(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"), // 마이크로초
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSS"), // 마이크로초
                // 투표 상세 조회에서 오는 endTime의 형식이 아래와 같음
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"), // 마이크로초
            )
        formatter.forEach {
            try {
                return LocalDateTime.parse(json.asString, it)
            } catch (_: DateTimeParseException) {
            }
        }
        throw JsonParseException("Unparseable date: ${json.asString}")
    }
}
