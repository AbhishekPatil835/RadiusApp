package com.android.radiusapp.android

import android.content.Context
import java.util.concurrent.TimeUnit

private const val PREFS_NAME = "radius_prefs"
private const val KEY_LAST_REFRESH_TIMESTAMP = "last_refresh_timestamp"

class DataRefreshManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun shouldRefreshData(): Boolean {
        val lastRefreshTimestamp = sharedPreferences.getLong(KEY_LAST_REFRESH_TIMESTAMP, 0)
        val currentTimeMillis = System.currentTimeMillis()
        val elapsedMillis = currentTimeMillis - lastRefreshTimestamp
        val oneDayMillis = TimeUnit.DAYS.toMillis(1)
        return elapsedMillis >= oneDayMillis
    }

    fun setLastRefreshTimestamp(timestamp: Long) {
        sharedPreferences.edit().putLong(KEY_LAST_REFRESH_TIMESTAMP, timestamp).apply()
    }
}
