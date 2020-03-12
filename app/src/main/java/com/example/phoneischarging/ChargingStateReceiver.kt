package com.example.phoneischarging

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast

class ChargingStateReceiver(private val wantedLevel: Int) : BroadcastReceiver() {
    private lateinit var vibrator: Vibrator

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val chargingLevel = level * 100 / scale.toFloat().toInt()

            Log.i("BRAHIM", "charging level  is $chargingLevel")

            if (chargingLevel == wantedLevel) {

                Toast.makeText(context,"CHARGED AS U WISH", Toast.LENGTH_SHORT).show()
                vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                val pattern = longArrayOf(0, 1000, 1000)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0))
                } else {
                    vibrator.vibrate(pattern, 0)
                }

            }
        }

    }
}