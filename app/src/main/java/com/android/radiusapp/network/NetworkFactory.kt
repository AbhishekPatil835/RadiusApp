package com.android.radiusapp.network


interface NetworkFactory {
    fun <T> getService( serviceClass: Class<T>): T
}

class NetworkFactoryImpl(private val retrofitFactory: RetrofitFactory) : NetworkFactory {
    override fun <T> getService(serviceClass: Class<T>): T {
        return retrofitFactory.getRetrofit().create(serviceClass)
    }
}
