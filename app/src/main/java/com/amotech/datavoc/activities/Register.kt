package com.amotech.datavoc.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amotech.datavoc.R
import com.amotech.datavoc.modals.User
import com.amotech.datavoc.services.AppPreferences
import com.amotech.datavoc.services.TopDialog
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    lateinit var topDialog: TopDialog
    lateinit var pref: AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        topDialog = TopDialog(this)
        pref = AppPreferences(this)
        register.setOnClickListener {
            if (isValid()) {
                topDialog.sneakSuccess("Well done")
                pref.setUserDetails(
                    User(
                        firstName.text.toString(),
                        lastName.text.toString(),
                        phone.text.toString(),
                        sensor.text.toString()
                    )
                )
                val intent = Intent(this, Activate::class.java)
                intent.putExtra("device", pref.getUserData().sensor_id)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }

        }
    }

    private fun isValid(): Boolean {
        if (firstName.text.isEmpty()) {
            topDialog.sneakError(getString(R.string.please) + "first name")
            return false
        } else if (firstName.text.length < 3) {
            topDialog.sneakWarning(getString(R.string.please_short) + "first name")
            return false
        }

        if (lastName.text.isEmpty()) {
            topDialog.sneakError(getString(R.string.please) + "last name")
            return false
        } else if (lastName.text.length < 3) {
            topDialog.sneakWarning(getString(R.string.please_short) + "last name")
            return false
        }

        if (phone.text.isEmpty()) {
            topDialog.sneakError(getString(R.string.please) + "contact")
            return false
        }

        if (sensor.text.isEmpty()) {
            topDialog.sneakError(getString(R.string.please) + "Sensor ID")
            return false
        }
        return true
    }
}