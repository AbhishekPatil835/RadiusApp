package com.android.radiusapp.android.database
import androidx.room.TypeConverter
import com.android.radiusapp.android.model.Exclusion
import com.android.radiusapp.android.model.Option
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromOptionDataList(options: List<Option>): String {
        return Gson().toJson(options)
    }

    @TypeConverter
    fun toOptionDataList(optionsString: String): List<Option> {
        val type = object : TypeToken<List<Option>>() {}.type
        return Gson().fromJson(optionsString, type)
    }

    @TypeConverter
    fun fromExclusionDataList(exclusions: List<Exclusion>): String {
        return Gson().toJson(exclusions)
    }

    @TypeConverter
    fun toExclusionDataList(exclusionsString: String): List<Exclusion> {
        val type = object : TypeToken<List<Exclusion>>() {}.type
        return Gson().fromJson(exclusionsString, type)
    }
}
