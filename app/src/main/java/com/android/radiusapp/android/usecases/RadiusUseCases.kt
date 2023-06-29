package com.android.radiusapp.android.usecases

import com.android.radiusapp.android.DataRefreshManager
import com.android.radiusapp.android.model.FacilitiesResponseModel
import com.android.radiusapp.android.repositories.RadiusRepository
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject
import java.net.HttpURLConnection
import javax.inject.Inject


@ViewModelScoped
class RadiusUseCases @Inject constructor(
    private val radiusRepository: RadiusRepository,
    private val dataRefreshManager: DataRefreshManager
) {
    fun invoke(): Single<RadiusResult> {
        return radiusRepository.getFacilitiesData()
            .map { response ->
                when {
                    response.isSuccessful -> {
                        val facilitiesResponseModel = response.body()
                        facilitiesResponseModel?.let {
                            RadiusResult.RadiusSuccess(it)
                        } ?: RadiusResult.UnknownError
                    }
                    response.code() in listOf(HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_BAD_REQUEST) -> {
                        val tempErrorBody = response.errorBody()?.string() ?: ""
                        val errorMessage = parseErrorMessage(tempErrorBody)
                        RadiusResult.RadiusFailure(errorMessage)
                    }
                    else -> RadiusResult.UnknownError
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun parseErrorMessage(errorBody: String): String {
        // Parse the error message from the errorBody if necessary
        return "Error"
    }
}
sealed class RadiusResult {
    data class RadiusSuccess(val facilitiesResponseModel: FacilitiesResponseModel) : RadiusResult()
    data class RadiusFailure(val errorMessage: String) : RadiusResult()
    object UnknownError : RadiusResult()
}