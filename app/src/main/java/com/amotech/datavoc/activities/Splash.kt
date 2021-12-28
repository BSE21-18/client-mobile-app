package com.amotech.datavoc.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.amotech.datavoc.R
import com.amotech.datavoc.services.AppPreferences

class Splash : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    lateinit var pref: AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        pref = AppPreferences(this)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Intent(this, Register::class.java)
            if (pref.getUserData().first_name.isNotEmpty())
                intent = Intent(this, Activate::class.java)
            startActivity(intent)
            finish()
        }, 3000)



    }
}