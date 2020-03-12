package com.example.phoneischarging

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

//    private lateinit var pendingIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        pendingIntent = getPendingIntent()

        validateLevel.setOnClickListener {
            val percentage = chargingLevelWanted.text.toString().toInt()
            Log.i("BRAHIM", "main activity $percentage")
            if ((percentage >= 10) and (percentage <= 100)) {
                val mIntent = Intent(this, MonitoringService::class.java)
                mIntent.putExtra("chargingWanted", percentage)

                startService(mIntent)



//                receiver = ChargingStateReceiver(percentage)
//                applicationContext.registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

            } else {
                chargingLevelWanted.setText("")
                Toast.makeText(applicationContext, "enter percentage within 10 and 100", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun getPendingIntent(): PendingIntent {
//        return PendingIntent.getBroadcast(this,
//                0,
//                Intent(this, ChargingStateReceiver::class.java),
//                PendingIntent.FLAG_UPDATE_CURRENT)
//    }

}
