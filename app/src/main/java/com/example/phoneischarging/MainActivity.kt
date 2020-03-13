package com.example.phoneischarging

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var chosenLevel = 10

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)

        val chargingLevelList = listOf(10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100)

        val chargingLevelAdapter = ChargingLevelAdapter(this, chargingLevelList)
        chargingLevelWanted.adapter = chargingLevelAdapter
        chargingLevelWanted.setSelection(0, false)

        chargingLevelWanted.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                chosenLevel = chargingLevelAdapter.getItem(position)

            }

            override fun onNothingSelected(parent: AdapterView<*>) = Unit
        }

        validateLevel.setOnClickListener {
            if (Power.isConnected(applicationContext)) {
                Toast.makeText(applicationContext, "YOUR PHONE IS CHARGING", Toast.LENGTH_SHORT).show()
                Log.i("BRAHIM", "main activity $chosenLevel")
                val mIntent = Intent(this, MonitoringService::class.java)
                mIntent.putExtra("chargingWanted", chosenLevel)
                startService(mIntent)
            } else {
                Toast.makeText(applicationContext, "YOUR PHONE IS NOT CHARGING", Toast.LENGTH_SHORT).show()
            }
        }

        stopService.setOnClickListener {
            stopService(Intent(this, MonitoringService::class.java))
            Toast.makeText(applicationContext, "MONITORING CHARGING LEVEL IS OFF", Toast.LENGTH_SHORT).show()
        }
    }

    object Power {
        fun isConnected(context: Context): Boolean {
            val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            val plugged = intent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS
        }
    }
}
