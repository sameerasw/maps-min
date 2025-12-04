package com.sameerasw.minmaps

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import rikka.shizuku.Shizuku

class MainActivity : AppCompatActivity() {

    private lateinit var shizukuStatusView: TextView
    private lateinit var notificationStatusView: TextView
    private lateinit var navigationStatusView: TextView

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            refreshStatus()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            fitsSystemWindows = true
        }

        val statusLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 24, 16, 16)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        shizukuStatusView = TextView(this).apply {
            textSize = 16f
            setPadding(0, 8, 0, 8)
        }
        statusLayout.addView(shizukuStatusView)

        notificationStatusView = TextView(this).apply {
            textSize = 16f
            setPadding(0, 8, 0, 8)
        }
        statusLayout.addView(notificationStatusView)

        navigationStatusView = TextView(this).apply {
            textSize = 16f
            setPadding(0, 8, 0, 8)
        }
        statusLayout.addView(navigationStatusView)

        val instructions = TextView(this).apply {
            text = "Enable Notification Listener in Settings, grant Shizuku permission, then start Maps navigation and lock your screen."
            textSize = 13f
            setPadding(0, 16, 0, 8)
        }
        statusLayout.addView(instructions)

        val settingsBtn = TextView(this).apply {
            text = "→ Notification Access Settings"
            textSize = 14f
            setPadding(16, 16, 16, 16)
            setBackgroundColor(0xFF6200EE.toInt())
            setTextColor(0xFFFFFFFF.toInt())
            isClickable = true
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
            }
        }
        mainLayout.addView(statusLayout)
        mainLayout.addView(settingsBtn)

        setContentView(mainLayout)
    }

    override fun onResume() {
        super.onResume()
        handler.post(updateRunnable)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateRunnable)
    }

    private fun refreshStatus() {
        shizukuStatusView.text = "Shizuku: ${if (hasShizukuPermission()) "✓" else "✗"}"
        notificationStatusView.text = "Listener: ${if (isNotificationListenerEnabled()) "✓" else "✗"}"
        navigationStatusView.text = "Navigation: ${if (MapsState.hasNavigationNotification) "✓" else "✗"}"
    }

    private fun hasShizukuPermission(): Boolean {
        return try {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        } catch (_: Exception) {
            false
        }
    }

    private fun isNotificationListenerEnabled(): Boolean {
        val enabledServices = Settings.Secure.getString(
            contentResolver,
            "enabled_notification_listeners"
        ) ?: return false
        val componentName = ComponentName(this, NotificationListener::class.java)
        return enabledServices.contains(componentName.flattenToString())
    }
}