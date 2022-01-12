package com.amotech.datavoc.services

import android.content.Context
import android.content.SharedPreferences
import com.amotech.datavoc.R
import com.amotech.datavoc.modals.User
import com.google.gson.Gson

class AppPreferences(context: Context) {
    private var prefs: SharedPreferences? = null

    companion object {
        const val KEY_USER = "KEY_USER"
        const val KEY_DEVICES = "KEY_DEVICES"
    }
    init {
        prefs = context.getSharedPreferences(context.getString(R.string.main), Context.MODE_PRIVATE)
    }

    fun setUserDetails(user: User) {
        val info = Gson().toJson(user)
        prefs!!.edit().putString(KEY_USER, info).apply()
        addDevice(user.snifferId)
    }


    private fun addDevice(device: String) {
        val data = getDevices()
        val searchItem = arrayListOf<String>()
        searchItem.addAll(data)
        if (!data.contains(device))
            searchItem.add(device)
        val editor: SharedPreferences.Editor = prefs!!.edit()
        val myJson = Gson().toJson(searchItem)
        editor.putString(KEY_DEVICES, myJson)
        editor.apply()
    }

    fun addSensorDevice(device: String):Boolean {
        val data = getDevices()
        val searchItem = arrayListOf<String>()
        searchItem.addAll(data)
        if (!data.contains(device)) {
            searchItem.add(device)
            val editor: SharedPreferences.Editor = prefs!!.edit()
            val myJson = Gson().toJson(searchItem)
            editor.putString(KEY_DEVICES, myJson)
            editor.apply()
            return true
        }
        return false

    }

    fun getDevices(): Array<String> {
        var data = arrayOf<String>()
        if (prefs!!.getString(KEY_DEVICES, null) !== null)
            data = Gson().fromJson(prefs!!.getString(KEY_DEVICES, null), Array<String>::class.java)
        return data
    }

    fun getUserData(): User{
        var userData = User("","","","")
        try {
            userData = Gson().fromJson<Any>(
                prefs!!.getString(KEY_USER, ""),
                User::class.java
            ) as User
        } catch (e: Exception) {
        }
        return userData
    }

}