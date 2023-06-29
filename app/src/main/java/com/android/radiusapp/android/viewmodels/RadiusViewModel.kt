package com.android.radiusapp.android.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.radiusapp.android.usecases.RadiusResult
import com.android.radiusapp.android.usecases.RadiusUseCases
import com.android.radiusapp.base_framework.network.Response
import com.android.radiusapp.base_framework.rx.Disposer
import com.android.radiusapp.base_framework.rx.RxDisposer
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class RadiusViewModel @Inject constructor(
    private val radiusUseCases: RadiusUseCases
) : ViewModel(), Disposer<Disposable> by RxDisposer() {

    private var _facilitiesLiveData: MutableLiveData<Response<RadiusResult>> = MutableLiveData()
    val facilitiesLiveData: LiveData<Response<RadiusResult>> = _facilitiesLiveData

    fun getRadiusData() {
        radiusUseCases
            .invoke()
            .doOnSubscribe {
                _facilitiesLiveData.postValue(Response.loading())
            }
            .subscribe(
                {
                    _facilitiesLiveData.postValue(Response.success(it)) },
                {
                    _facilitiesLiveData.postValue(Response.error(it))
                    Timber.e(it)

                }
            ).collect()
    }

}