package com.android.radiusapp.di

import android.app.Application
import androidx.room.Room
import com.android.radiusapp.android.DataRefreshManager
import com.android.radiusapp.android.database.FacilityDao
import com.android.radiusapp.android.database.RadiusDatabase
import com.android.radiusapp.network.OkHttpClientFactory
import com.android.radiusapp.network.OkHttpClientFactoryDependencyImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClientFactoryDependency(
    ): OkHttpClientFactory.Dependency {
        return OkHttpClientFactoryDependencyImpl()
    }

    @Provides
    @Singleton
    fun provideDataRefreshManager(application: Application): DataRefreshManager {
        return DataRefreshManager(application)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): RadiusDatabase {
        return Room.databaseBuilder(application, RadiusDatabase::class.java, "app_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideFacilityDao(appDatabase: RadiusDatabase): FacilityDao {
        return appDatabase.facilityDao()
    }


}