package com.android.radiusapp.android.model



import com.google.gson.annotations.SerializedName

data class FacilitiesResponseModel(
    @SerializedName("exclusions")
    val exclusions: ArrayList<ArrayList<Exclusion>>,
    @SerializedName("facilities")
    val facilities: ArrayList<Facility>
)

data class Facility(
    @SerializedName("facility_id")
    val facilityId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("options")
    val options: ArrayList<OptionData>
)


data class Option(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)


data class Exclusion(
    @SerializedName("facility_id")
    val facilityId: String,
    @SerializedName("options_id")
    val optionsId: String
)