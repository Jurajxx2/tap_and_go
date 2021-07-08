package com.example.tapandgo.helpers

import android.util.Log
import com.example.tapandgo.BuildConfig

object Smartlog {
    fun log(log: String) {
        if (BuildConfig.DEBUG) {
            Log.d("TAPX", log)
        }
    }
}