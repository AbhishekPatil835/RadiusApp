package com.android.radiusapp.network

import okhttp3.Interceptor
import okhttp3.Response


class HttpHeadersInterceptor (
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        return chain.proceed(requestBuilder.build())
    }
}