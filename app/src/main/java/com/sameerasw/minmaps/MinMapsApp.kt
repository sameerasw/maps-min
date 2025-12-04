package com.sameerasw.minmaps

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.os.Build

class MinMapsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        ShizukuUtils.initialize()

        val screenOffReceiver = ScreenOffReceiver()
        val intentFilter = IntentFilter(android.content.Intent.ACTION_SCREEN_OFF)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(screenOffReceiver, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(screenOffReceiver, intentFilter)
        }
    }
}