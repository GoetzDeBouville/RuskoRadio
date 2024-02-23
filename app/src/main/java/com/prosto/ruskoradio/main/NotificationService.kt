package com.prosto.ruskoradio.main

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.prosto.ruskoradio.R

class NotificationService(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun showNotification() {
        val activityIntent = Intent(context, MainActivity::class.java)
        val notificationPendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.on_air))
            .setContentIntent(notificationPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(null)
            .setVibrate(null)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(
            NOTIFICATION_ID,
            notification
        )
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "Rusko Radio"
    }
}