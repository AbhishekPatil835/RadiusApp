package com.android.radiusapp.base_framework.rx

interface Disposer<T> {
    fun T.collect()
    fun dispose()
}