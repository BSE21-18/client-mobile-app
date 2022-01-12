package com.amotech.datavoc.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.amotech.datavoc.R
import com.amotech.datavoc.services.AppPreferences
import kotlinx.android.synthetic.main.activity_activate.*
import java.util.*

class Activate : AppCompatActivity() {
    lateinit var pref: AppPreferences

    override fun onResume() {
        connecting.visibility = GONE
        connect.visibility = VISIBLE
        super.onResume()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activate)

        val deviceStr = intent.getStringExtra("device")

        connecting.visibility = GONE
        connect.visibility = VISIBLE
        pref = AppPreferences(this)
        preparing.text = getString(R.string.preparing) + " " + deviceStr!!.uppercase(Locale.getDefault())
        welcome.text =
            getString(R.string.hello) + " " + pref.getUserData().firstname + " " + pref.getUserData().lastname
        device.text =
            getString(R.string.activated_successfully) + " " + deviceStr.uppercase(
                Locale.getDefault()
            )

        deviceConnect.setOnClickListener {
            connecting.visibility = VISIBLE
            connect.visibility = GONE

            Handler(Looper.getMainLooper()).postDelayed({
                if (connecting.isVisible) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("device", deviceStr)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }, 1000)
        }

        cancelConnect.setOnClickListener {
            connecting.visibility = GONE
            connect.visibility = VISIBLE
        }

        notNow.setOnClickListener {
            onBackPressed()
        }
    }
}