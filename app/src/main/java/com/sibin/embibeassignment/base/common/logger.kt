package com.sibin.embibeassignment.base.common

import android.util.Log
import com.sibin.embibeassignment.BuildConfig

internal var isLoggingEnabled: Boolean = BuildConfig.DEBUG

internal inline fun <reified T : Any> T.logDebug(log: String) {
    if (isLoggingEnabled) {
        Log.d(this::class.java.simpleName, log)
    }
}