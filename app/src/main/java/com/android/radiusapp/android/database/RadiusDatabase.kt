package com.android.radiusapp.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.radiusapp.android.model.FacilityData

@Database(entities = [FacilityData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RadiusDatabase : RoomDatabase() {
    abstract fun facilityDao(): FacilityDao
}
