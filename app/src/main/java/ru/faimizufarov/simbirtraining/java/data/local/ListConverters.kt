package ru.faimizufarov.simbirtraining.java.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListConverters {
    @TypeConverter
    fun fromStringList(stringList: List<String>): String {
        return Json.encodeToString(stringList)
    }

    @TypeConverter
    fun toStringList(string: String): List<String> {
        return Json.decodeFromString(string)
    }
}
