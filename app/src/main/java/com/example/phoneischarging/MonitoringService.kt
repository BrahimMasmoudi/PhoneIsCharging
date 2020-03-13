package com.example.phoneischarging

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.os.Vibrator
import android.util.Log

class MonitoringService : Service() {

    private lateinit var receiver: ChargingStateReceiver

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val wantedLevel = intent?.getIntExtra("chargingWanted", 0) ?: 0
        receiver = ChargingStateReceiver(wantedLevel)
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiver, filter)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.i("BRAHIM", "DESTROYING SERVICE")
        super.onDestroy()
    }
}
