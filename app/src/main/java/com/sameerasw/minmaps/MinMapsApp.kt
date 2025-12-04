package com.sameersandakelum.mapsmin

import android.app.Application

class MinMapsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ShizukuUtils.initialize()
    }
}