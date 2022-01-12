package com.amotech.datavoc.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.amotech.datavoc.R
import com.amotech.datavoc.modals.User
import com.amotech.datavoc.services.APIService
import com.amotech.datavoc.services.AppPreferences
import com.amotech.datavoc.services.ServiceBuilder
import com.amotech.datavoc.services.TopDialog
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.*

class Register : AppCompatActivity() {
    lateinit var topDialog: TopDialog
    lateinit var pref: AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        avi.visibility = View.GONE
        topDialog = TopDialog(this)
        pref = AppPreferences(this)
        ccp.registerCarrierNumberEditText(etPhone)
        isPhone.visibility = View.GONE
        etPhone.addTextChangedListener {
            if (ccp.isValidFullNumber)
                isPhone.visibility = View.VISIBLE
            else
                isPhone.visibility = View.GONE
        }

        register.setOnClickListener {
            if (isValid()) {
                register()
            }

        }
    }

    private fun register() {
        avi.visibility = View.VISIBLE
        val service = HashMap<String, Any>()
        service[getString(R.string.firstname)] = firstName.text.toString()
        service[getString(R.string.lastname)] = lastName.text.toString()
        service[getString(R.string.contact)] =
            ccp.selectedCountryCode + etPhone.text.trim().toString().replace("\\s".toRegex(), "")
        service[getString(R.string.snifferId)] = sensor.text.toString()


        val destinationService = ServiceBuilder.buildService(APIService::class.java)
        val requestCall = destinationService.postUser(service)

        requestCall.enqueue(object :
            Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>,
            ) {
                avi.visibility = View.GONE
                try {
                    if (response.body()!!.firstname.isNotEmpty()) {
                        topDialog.sneakSuccess("Well done")
                        pref.setUserDetails(
                            User(
                                firstName.text.toString(),
                                lastName.text.toString(),
                                ccp.selectedCountryCode + etPhone.text.trim().toString()
                                    .replace("\\s".toRegex(), ""),
                                sensor.text.toString()
                            )
                        )
                        val intent = Intent(this@Register, Activate::class.java)
                        intent.putExtra("device", pref.getUserData().snifferId)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        topDialog.sneakError("Invalid input")
                    }
                } catch (e: Exception) {
                }
            }

            override fun onFailure(
                call: Call<User>,
                t: Throwable,
            ) {
                avi.visibility = View.GONE
                topDialog.sneakError(t.message.toString())
            }

        })
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

        if (!ccp.isValidFullNumber) {
            topDialog.sneakError(getString(R.string.please_short) + "Contact")
            return false
        }

        if (sensor.text.isEmpty()) {
            topDialog.sneakError(getString(R.string.please) + "Sensor ID")
            return false
        }
        return true
    }
}