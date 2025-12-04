package com.sameerasw.minmaps

import android.content.ComponentName
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.sameersw.mapsmin.R
import rikka.shizuku.Shizuku

class MinMapsQSTile : TileService() {

    override fun onStartListening() {
        updateTileState()
    }

    override fun onClick() {
        if (hasRequiredPermissions()) {
            MapsState.isEnabled = !MapsState.isEnabled
            updateTileState()
        }
    }

    private fun updateTileState() {
        val tile = qsTile ?: return

        tile.label = "Maps Power Saving"

        if (!hasRequiredPermissions()) {
            tile.state = Tile.STATE_UNAVAILABLE
            tile.subtitle = "Permissions required"
            tile.icon = Icon.createWithResource(this, R.drawable.rounded_navigation_24)
        } else if (MapsState.isEnabled) {
            tile.state = Tile.STATE_ACTIVE
            tile.subtitle = "Enabled"
            tile.icon = Icon.createWithResource(this, R.drawable.round_navigation_on)
        } else {
            tile.state = Tile.STATE_INACTIVE
            tile.subtitle = "Disabled"
            tile.icon = Icon.createWithResource(this, R.drawable.rounded_navigation_24)
        }

        tile.updateTile()
    }

    private fun hasRequiredPermissions(): Boolean {
        val hasShizuku = try {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        } catch (_: Exception) {
            false
        }

        val hasNotificationListener = try {
            val enabledServices = Settings.Secure.getString(
                contentResolver,
                "enabled_notification_listeners"
            ) ?: ""
            val componentName = ComponentName(this, NotificationListener::class.java)
            enabledServices.contains(componentName.flattenToString())
        } catch (_: Exception) {
            false
        }

        return hasShizuku && hasNotificationListener
    }
}

