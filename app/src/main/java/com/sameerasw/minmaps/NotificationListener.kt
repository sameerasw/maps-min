package com.sameerasw.minmaps

import android.app.Notification
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.google.android.apps.maps") {
            if (isNavigationNotification(sbn)) {
                MapsState.hasNavigationNotification = true
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.google.android.apps.maps") {
            if (isNavigationNotification(sbn)) {
                MapsState.hasNavigationNotification = false
            }
        }
    }

    private fun isNavigationNotification(sbn: StatusBarNotification): Boolean {
        val notification = sbn.notification
        return notification.channelId?.contains("navigation", ignoreCase = true) == true ||
                sbn.key?.contains("navigation", ignoreCase = true) == true ||
                checkNotificationTitle(notification) ||
                checkNotificationText(notification)
    }

    private fun checkNotificationTitle(notification: Notification): Boolean {
        return try {
            val extras = notification.extras ?: Bundle()
            val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
            title.contains("navigation", ignoreCase = true)
        } catch (e: Exception) {
            false
        }
    }

    private fun checkNotificationText(notification: Notification): Boolean {
        return try {
            val extras = notification.extras ?: Bundle()
            val text = extras.getString(Notification.EXTRA_TEXT) ?: ""
            text.contains("navigation", ignoreCase = true)
        } catch (e: Exception) {
            false
        }
    }
}