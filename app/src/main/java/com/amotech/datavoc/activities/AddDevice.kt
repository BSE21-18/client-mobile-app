package com.amotech.datavoc.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amotech.datavoc.R
import com.amotech.datavoc.services.AppPreferences
import com.amotech.datavoc.services.TopDialog
import kotlinx.android.synthetic.main.activity_add_device.*

class AddDevice : AppCompatActivity() {
    lateinit var pref:AppPreferences
    lateinit var topDialog: TopDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        pref = AppPreferences(this)
        topDialog = TopDialog(this)
        register_device.setOnClickListener {
            if(pref.addSensorDevice(device.text.toString())){
                topDialog.sneakSuccess("Device added successfully")
                val intent = Intent(this, Devices::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }else{
                topDialog.sneakError("Device already exists")
            }
        }
        exit.setOnClickListener {
            onBackPressed()
            finish()
        }
    }
}