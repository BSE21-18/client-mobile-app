package com.amotech.datavoc.services

import android.content.Context
import android.content.SharedPreferences
import com.amotech.datavoc.R
import com.amotech.datavoc.modals.User
import com.google.gson.Gson

class AppPreferences(context: Context) {
    private var prefs: SharedPreferences? = null

    companion object {
        const val KEY_USER = "KEY_IS_FIRST_TIME"
    }
    init {
        prefs = context.getSharedPreferences(context.getString(R.string.main), Context.MODE_PRIVATE)
    }

    fun setUserDetails(user: User) {
        val info = Gson().toJson(user)
        prefs!!.edit().putString(KEY_USER, info).apply()
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