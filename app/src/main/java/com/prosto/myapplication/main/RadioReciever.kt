package com.prosto.myapplication.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RadioReciever : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = NotificationService(context)
        service.showNotification()
    }
}
