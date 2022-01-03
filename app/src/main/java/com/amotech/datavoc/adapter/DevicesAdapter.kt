package com.amotech.datavoc.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.amotech.datavoc.R
import com.amotech.datavoc.R.string.*
import com.amotech.datavoc.activities.Activate
import com.amotech.datavoc.modals.Result
import java.util.*

class DevicesAdapter(private var devices: Array<String>) :
    RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {
    lateinit var mContext: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var device =  itemView.findViewById<TextView>(R.id.device)
        init {

            mContext = itemView.context
            itemView.setOnClickListener {
                val i = Intent(mContext, Activate::class.java)
                i.putExtra("device", devices[adapterPosition])
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                mContext.startActivity(i)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_devices, parent, false)
        return ViewHolder(v)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = devices[position]
        holder.device.text = data.uppercase(Locale.getDefault())

    }

    override fun getItemCount(): Int {
        return devices.size
    }
}
