package com.android.radiusapp.android.di

import com.android.radiusapp.android.repositories.RadiusApi
import com.android.radiusapp.network.NetworkFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RadiusApiModule {

    @Provides
    @ViewModelScoped
    fun provideRadiusApi(
        networkFactory: NetworkFactory
    ): RadiusApi {
        return networkFactory.getService(RadiusApi::class.java)
    }



}