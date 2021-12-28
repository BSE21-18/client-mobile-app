package com.amotech.datavoc.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.amotech.datavoc.R
import com.amotech.datavoc.adapter.DevicesAdapter
import com.amotech.datavoc.adapter.ResultAdapter
import com.amotech.datavoc.modals.Result
import com.amotech.datavoc.services.AppPreferences
import kotlinx.android.synthetic.main.activity_main.*

class Devices : AppCompatActivity() {
    lateinit var pref:AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devices)


        pref = AppPreferences(this)

        recyclerView.layoutManager = (LinearLayoutManager(this))

        val adapter = DevicesAdapter(pref.getDevices())
        recyclerView.adapter = adapter


    }
}