package com.android.radiusapp.android.repositories

import com.android.radiusapp.android.DataRefreshManager
import com.android.radiusapp.android.database.FacilityDao
import com.android.radiusapp.android.model.Exclusion
import com.android.radiusapp.android.model.ExclusionData
import com.android.radiusapp.android.model.FacilityData
import com.android.radiusapp.android.model.FacilitiesResponseModel
import com.android.radiusapp.android.model.Facility
import com.android.radiusapp.android.model.Option
import com.android.radiusapp.android.model.OptionData
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

class RadiusRepository @Inject constructor(
    private val radiusApi: RadiusApi,
    private val facilityDao: FacilityDao,
    private val dataRefreshManager: DataRefreshManager
) {
    fun getFacilitiesData(): Single<Response<FacilitiesResponseModel>> {
        if (dataRefreshManager.shouldRefreshData()) {
            return radiusApi.getFacilityData()
                .doOnSuccess { response ->
                    if (response.isSuccessful) {
                        val facilitiesResponse = response.body()
                        facilitiesResponse?.let {
                            // Save data to RoomDatabase
                            val facilityDataList = convertFacilitiesResponseToEntityList(it)
                            facilityDao.insertFacilities(facilityDataList)
                            // Update last refresh timestamp
                            dataRefreshManager.setLastRefreshTimestamp(System.currentTimeMillis())
                        }
                    }
                }
        } else {
            // Retrieve data from RoomDatabase
            return facilityDao.getAllFacilities()
                .flatMap { facilityDataList ->
                    val facilitiesResponseModel = convertFacilityDataToResponseModel(facilityDataList)
                    val response = Response.success(facilitiesResponseModel)
                    Single.just(response)
                }
        }
    }

    private fun convertFacilitiesResponseToEntityList(response: FacilitiesResponseModel): List<FacilityData> {
        val facilityDataList = mutableListOf<FacilityData>()

        for (facility in response.facilities) {
            val options = mutableListOf<OptionData>()
            for (option in facility.options) {
                val optionData = OptionData(option.icon, option.id, option.name)
                options.add(optionData)
            }

            val exclusions = mutableListOf<ExclusionData>()
            for (exclusionList in response.exclusions) {
                for (exclusion in exclusionList) {
                    if (exclusion.facilityId == facility.facilityId) {
                        val exclusionData = ExclusionData(exclusion.facilityId, exclusion.optionsId)
                        exclusions.add(exclusionData)
                    }
                }
            }

            val facilityData = FacilityData(facility.facilityId, facility.name, options, exclusions)
            facilityDataList.add(facilityData)
        }

        return facilityDataList
    }

    private fun convertFacilityDataToResponseModel(facilityDataList: List<FacilityData>): FacilitiesResponseModel {
        val exclusionsMap = mutableMapOf<String, MutableList<Exclusion>>()
        val facilities = mutableListOf<Facility>()

        for (facilityData in facilityDataList) {
            val options = mutableListOf<Option>()

            for (optionData in facilityData.options) {
                val option = Option(optionData.icon, optionData.id, optionData.name)
                options.add(option)
            }

            for (exclusionData in facilityData.exclusions) {
                val exclusion = Exclusion(exclusionData.facilityId, exclusionData.optionsId)
                if (exclusionsMap.containsKey(exclusionData.facilityId)) {
                    exclusionsMap[exclusionData.facilityId]?.add(exclusion)
                } else {
                    val exclusionList = mutableListOf(exclusion)
                    exclusionsMap[exclusionData.facilityId] = exclusionList
                }
            }

            val facility = Facility(facilityData.id, facilityData.name,
                facilityData.options as ArrayList<OptionData>
            )
            facilities.add(facility)
        }

        val exclusions = exclusionsMap.values.toList()

        return FacilitiesResponseModel(exclusions as ArrayList<ArrayList<Exclusion>>,
            facilities as ArrayList<Facility>
        )
    }

}
