package com.sameersandakelum.mapsmin

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.google.android.apps.maps" && sbn.notification.channelId == "NAVIGATION_NOTIFICATION") {
            MapsState.hasNavigationNotification = true
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.google.android.apps.maps" && sbn.notification.channelId == "NAVIGATION_NOTIFICATION") {
            MapsState.hasNavigationNotification = false
        }
    }
}