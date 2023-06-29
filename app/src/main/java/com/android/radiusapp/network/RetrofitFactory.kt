package com.android.radiusapp.network

import android.os.StrictMode
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://my-json-server.typicode.com/"
class RetrofitFactory(
    private val okHttpClientFactory: OkHttpClientFactory
) {
    private lateinit var retrofit : Retrofit
    fun getRetrofit(): Retrofit {
           synchronized(NetworkFactory::class.java) {
                    StrictMode.noteSlowCall("Retrofit initialization slow call")
                   retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(Gson()))
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .client(okHttpClientFactory.getOkHttpClient())
                        .build()
                }
       return retrofit
    }
}