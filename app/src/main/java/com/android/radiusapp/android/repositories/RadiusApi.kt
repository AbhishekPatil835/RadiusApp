package com.android.radiusapp.android.repositories

import com.android.radiusapp.android.model.FacilitiesResponseModel
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET


const val GET_FACILITY_DATA = "iranjith4/ad-assignment/db"
interface RadiusApi {

    @GET(GET_FACILITY_DATA)
    fun getFacilityData(): Single<Response<FacilitiesResponseModel>>
}