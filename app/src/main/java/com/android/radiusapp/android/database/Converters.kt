package com.android.radiusapp.android.database
import androidx.room.TypeConverter
import com.android.radiusapp.android.model.ExclusionData
import com.android.radiusapp.android.model.OptionData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromOptionDataList(options: List<OptionData>): String {
        return Gson().toJson(options)
    }

    @TypeConverter
    fun toOptionDataList(optionsString: String): List<OptionData> {
        val type = object : TypeToken<List<OptionData>>() {}.type
        return Gson().fromJson(optionsString, type)
    }

    @TypeConverter
    fun fromExclusionDataList(exclusions: List<ExclusionData>): String {
        return Gson().toJson(exclusions)
    }

    @TypeConverter
    fun toExclusionDataList(exclusionsString: String): List<ExclusionData> {
        val type = object : TypeToken<List<ExclusionData>>() {}.type
        return Gson().fromJson(exclusionsString, type)
    }
}
