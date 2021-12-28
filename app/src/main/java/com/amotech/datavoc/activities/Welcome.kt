package com.amotech.datavoc.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amotech.datavoc.R
import com.amotech.datavoc.services.AppPreferences
import kotlinx.android.synthetic.main.activity_welcome.*

class Welcome : AppCompatActivity() {
    lateinit var pref:AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        pref = AppPreferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        exit.setOnClickListener {
            onBackPressed()
        }

        connect_to_sensor.setOnClickListener {
            val devices = pref.getDevices()
            if(devices.size == 1){
                val intent = Intent(this, Activate::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else{

            }
            Log.d("yugsjdhf", pref.getDevices().size.toString())
            val intent = Intent(this, Welcome::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}