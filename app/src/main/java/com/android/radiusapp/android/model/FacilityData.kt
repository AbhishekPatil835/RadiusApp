package com.android.radiusapp.android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facility_data")
data class FacilityData(
    @PrimaryKey val id: String,
    val name: String,
    val options: List<OptionData>,
    val exclusions: List<ExclusionData>
    // Include other properties as needed
)

data class ExclusionData(
    val facilityId: String,
    val optionsId: String
)

data class OptionData(
    val icon: String,
    val id: String,
    val name: String
)


