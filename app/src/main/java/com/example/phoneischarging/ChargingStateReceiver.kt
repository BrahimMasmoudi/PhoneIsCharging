package com.example.phoneischarging

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast

class ChargingStateReceiver(private val wantedLevel: Int) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val chargingLevel = level * 100 / scale.toFloat().toInt()
            Toast.makeText(context, "${chargingLevel}%", Toast.LENGTH_SHORT).show()

            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager?
            val isScreenOn = pm!!.isInteractive

            if (!isScreenOn && chargingLevel >= wantedLevel) {
                context.startActivity(Intent(context, MainActivity::class.java))
                Toast.makeText(context, "Charged as u wished", Toast.LENGTH_SHORT).show()

            }

            if (chargingLevel >= wantedLevel) {
                Toast.makeText(context,"Charged as u wished",Toast.LENGTH_SHORT).show()
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