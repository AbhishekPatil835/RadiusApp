package com.android.radiusapp.android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facility_data")
data class FacilityData(
    @PrimaryKey val id: String,
    val name: String,
    val options: List<Option>,
    val exclusions: List<Exclusion>
    // Include other properties as needed
)


