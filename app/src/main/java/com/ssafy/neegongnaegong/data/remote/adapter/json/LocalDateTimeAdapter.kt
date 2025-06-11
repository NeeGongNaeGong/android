package com.ssafy.neegongnaegong.data.remote.adapter.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateTimeAdapter :
    JsonSerializer<LocalDateTime>,
    JsonDeserializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    override fun serialize(
        src: LocalDateTime,
        typeOfSrc: Type,
        context: JsonSerializationContext,
    ): JsonElement = JsonPrimitive(src.format(formatter))

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): LocalDateTime {
        val value = json.asString
        return try {
            // 나노초 포함된 포맷 처리
            LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } catch (e: DateTimeParseException) {
            // 밀리초 이하가 없는 경우 fallback
            LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        }
    }
}
