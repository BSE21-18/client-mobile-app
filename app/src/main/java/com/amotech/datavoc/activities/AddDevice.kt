package com.amotech.datavoc.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amotech.datavoc.R
import com.amotech.datavoc.modals.User
import com.amotech.datavoc.services.APIService
import com.amotech.datavoc.services.AppPreferences
import com.amotech.datavoc.services.ServiceBuilder
import com.amotech.datavoc.services.TopDialog
import kotlinx.android.synthetic.main.activity_add_device.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

class AddDevice : AppCompatActivity() {
    lateinit var pref:AppPreferences
    lateinit var topDialog: TopDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        pref = AppPreferences(this)
        topDialog = TopDialog(this)
        aviReg.visibility = View.GONE
        add_devices.setOnClickListener {
            if(pref.addSensorDevice(device.text.toString())){
                register()
            }else{
                topDialog.sneakError("Device already exists")
            }
        }
        exit.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun register() {
        aviReg.visibility = View.VISIBLE
        exit.visibility = View.GONE

        val service = HashMap<String, Any>()
        service[getString(R.string.firstname)] = pref.getUserData().firstname
        service[getString(R.string.lastname)] = pref.getUserData().lastname
        service[getString(R.string.contact)] = pref.getUserData().phone
        service[getString(R.string.snifferId)] = device.text.toString()
            .uppercase(Locale.getDefault())

        val destinationService = ServiceBuilder.buildService(APIService::class.java)
        val requestCall = destinationService.postUser(service)

        requestCall.enqueue(object :
            Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>,
            ) {
                exit.visibility = View.VISIBLE
                aviReg.visibility = View.GONE
                try {
                    if (response.body()!!.firstname.isNotEmpty()) {
                        topDialog.sneakSuccess("Device added successfully")
                        val intent = Intent(this@AddDevice, Devices::class.java)
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
                aviReg.visibility = View.GONE
                exit.visibility = View.VISIBLE
                topDialog.sneakError(t.message.toString())
            }

        })
    }

}