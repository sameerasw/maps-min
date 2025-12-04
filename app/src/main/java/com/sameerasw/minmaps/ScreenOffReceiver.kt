package com.sameerasw.minmaps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ScreenOffReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_OFF &&
            MapsState.isEnabled &&
            MapsState.hasNavigationNotification) {
            ShizukuUtils.runCommand("am start -n com.google.android.apps.maps/com.google.android.apps.gmm.features.minmode.MinModeActivity")
        }
    }
}