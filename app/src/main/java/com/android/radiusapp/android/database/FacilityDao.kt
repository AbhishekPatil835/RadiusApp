package com.android.radiusapp.android.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.radiusapp.android.model.FacilityData
import io.reactivex.rxjava3.core.Single

@Dao
interface FacilityDao {
    @Query("SELECT * FROM facility_data")
    fun getAllFacilities(): Single<List<FacilityData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFacilities(facilities: List<FacilityData>)
}
