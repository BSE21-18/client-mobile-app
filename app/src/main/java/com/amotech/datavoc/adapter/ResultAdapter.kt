package com.amotech.datavoc.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.amotech.datavoc.R
import com.amotech.datavoc.R.string.*
import com.amotech.datavoc.modals.DATAVOC

class ResultAdapter(private var result: MutableList<DATAVOC>) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    lateinit var mContext: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.date)
        var time: TextView = itemView.findViewById(R.id.time)
        var sensor: TextView = itemView.findViewById(R.id.sensor)
        var detection: TextView = itemView.findViewById(R.id.detection)
        var status: TextView = itemView.findViewById(R.id.status)
        var recommendation: TextView = itemView.findViewById(R.id.recommendation)
//        var profilePic: ImageView = itemView.findViewById(R.id.profile_pic)

        init {
            mContext = itemView.context
            itemView.setOnClickListener {

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_result, parent, false)
        return ViewHolder(v)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = result[position]
        var status = "Healthy"
        var color = mContext.getColor(R.color.white)
        var design = mContext.getDrawable(R.drawable.healthy)
        holder.date.text = mContext.getString(date) + " " + data.date
        holder.time.text = mContext.getString(time) + " " + data.time
        holder.sensor.text = mContext.getString(sensor_device_used) + " " + data.sniffer
        holder.detection.text = mContext.getString(detection_of) + " " + data.disease

//        if (data.status == 1) {
//            status = "healthy"
//            color = mContext.getColor(R.color.white)
//            design = mContext.getDrawable(R.drawable.healthy)
//        } else if (data.status == 2) {
//            status = "mild +ve"
//            color = mContext.getColor(R.color.black)
//            design = mContext.getDrawable(R.drawable.mild_pos)
//        }
        holder.status.setTextColor(color)
        holder.status.text = data.plantStatus
        holder.status.background = design
        holder.recommendation.text = data.recommendation

    }

    override fun getItemCount(): Int {
        return result.size
    }
}
