package com.amotech.datavoc.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amotech.datavoc.R
import com.amotech.datavoc.services.AppPreferences
import com.amotech.datavoc.services.TopDialog
import kotlinx.android.synthetic.main.activity_welcome.*

class Welcome : AppCompatActivity() {
    lateinit var pref:AppPreferences
    private lateinit var topDialog: TopDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        pref = AppPreferences(this)
        topDialog = TopDialog(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        exit.setOnClickListener {
            onBackPressed()
            finish()
        }

        register_device.setOnClickListener {
            val intent = Intent(this, AddDevice::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        connect_to_sensor.setOnClickListener {
            val devices = pref.getDevices()
            if(devices.size == 1){
                val intent = Intent(this, Activate::class.java)
                intent.putExtra("device", devices[0])
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else{
                val intent = Intent(this, Devices::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

        }
    }
}