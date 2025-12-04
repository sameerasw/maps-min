package com.sameerasw.minmaps

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.google.android.apps.maps") {
            if (isNavigationNotification(sbn)) {
                MapsState.hasNavigationNotification = true
            } else {
                MapsState.hasNavigationNotification = false
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.google.android.apps.maps") {
            MapsState.hasNavigationNotification = false
        }
    }

    private fun isNavigationNotification(sbn: StatusBarNotification): Boolean {
        val notification = sbn.notification

        // Check if it's a persistent notification (foreground service notification)
        if (!isPersistentNotification(notification)) {
            return false
        }

        // Check for Navigation category
        return hasNavigationCategory(notification)
    }

    private fun isPersistentNotification(notification: Notification): Boolean {
        // Persistent notifications have FLAG_ONGOING_EVENT flag set
        return (notification.flags and Notification.FLAG_ONGOING_EVENT) != 0
    }

    private fun hasNavigationCategory(notification: Notification): Boolean {
        // Check notification category
        val category = notification.category ?: return false

        // Use regex to match "Navigation" category (case-insensitive)
        val navigationRegex = Regex("(?i).*navigation.*")
        return navigationRegex.containsMatchIn(category)
    }
}