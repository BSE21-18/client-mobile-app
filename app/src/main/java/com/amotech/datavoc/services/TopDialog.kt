package com.amotech.datavoc.services

import android.app.Activity
import com.amotech.datavoc.R
import com.irozon.sneaker.Sneaker

class TopDialog(activity: Activity) {
    private val mActivity = activity
    fun sneakError(desc: String) {
        Sneaker.with(mActivity)
            .setTitle(desc, R.color.white)
            .setDuration(3000)
            .sneakError()
    }

    fun sneakSuccess(desc: String) {
        Sneaker.with(mActivity)
            .setTitle(desc, R.color.white)
            .setDuration(3000)
            .sneakSuccess()
    }

    fun sneakWarning(desc: String) {
        Sneaker.with(mActivity)
            .setTitle(desc, R.color.white)
            .setDuration(3000)
            .sneakWarning()
    }
}