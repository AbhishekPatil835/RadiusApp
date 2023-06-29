package com.bookmyshow.multiverseofmovies.network.di

import android.content.Context
import com.android.radiusapp.network.NetworkFactory
import com.android.radiusapp.network.NetworkFactoryImpl
import com.android.radiusapp.network.OkHttpClientFactory
import com.android.radiusapp.network.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkFactory(retrofitFactory: RetrofitFactory): NetworkFactory {
        return NetworkFactoryImpl(retrofitFactory)
    }

    @Provides
    @Singleton
    fun providesRetrofitFactory(
        okHttpClientFactory: OkHttpClientFactory
    ): RetrofitFactory {
        return RetrofitFactory(okHttpClientFactory)
    }

    @Provides
    @Singleton
    fun providesOkHttpClientFactory(
        @ApplicationContext context: Context,
        dependency: OkHttpClientFactory.Dependency
    ) : OkHttpClientFactory {
        return OkHttpClientFactory(context, dependency)
    }
}