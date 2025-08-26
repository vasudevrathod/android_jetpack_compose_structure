package com.vaasudev.androidcomposestructure.domain.utility

import android.util.Log
import com.vaasudev.androidcomposestructure.BuildConfig

fun printLog(tag: String = "Log Information", value: Any) {
    if (BuildConfig.DEBUG) {
        Log.i(tag, "$tag Log =====> 🧐🧐🧐 $value")
    }
}