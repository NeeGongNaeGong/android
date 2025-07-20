package com.ssafy.neegongnaegong.data.local.database.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun fromLocalDateTimeToJson(localDateTime: LocalDateTime?): String? = localDateTime?.toString()

    @TypeConverter
    fun fromJsonToLocalDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }
}
