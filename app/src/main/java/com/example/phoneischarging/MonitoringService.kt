package com.example.phoneischarging

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log


class MonitoringService : Service() {

    private lateinit var receiver: ChargingStateReceiver

//    override fun onCreate() {
//        super.onCreate()
////        val wantedLevel = serviceIntent?.getIntExtra("chargingWanted", 0)?: 0
////        Log.i("BRAHIM", "service $wantedLevel")
//        receiver = ChargingStateReceiver(100)
//        val filter = IntentFilter()
//        filter.addAction(Intent.ACTION_BATTERY_CHANGED)
//        registerReceiver(receiver, filter)
//    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val wantedLevel = intent?.getIntExtra("chargingWanted", 0) ?: 0
        receiver = ChargingStateReceiver(wantedLevel)
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiver, filter)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        Log.i("BRAHIM", "DESTROYING SERVICE")
        super.onDestroy()
    }
}
