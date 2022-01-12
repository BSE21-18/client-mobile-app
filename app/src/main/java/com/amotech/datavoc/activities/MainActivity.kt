package com.amotech.datavoc.activities

import android.os.Bundle
import android.util.Log
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amotech.datavoc.R
import com.amotech.datavoc.adapter.ResultAdapter
import com.amotech.datavoc.modals.DATAVOC
import com.amotech.datavoc.services.AppPreferences
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var deviceStr = ""
    private var phone = ""
    private lateinit var pref:AppPreferences
    private lateinit var webSocketClient: WebSocketClient
    private var results: MutableList<DATAVOC> = ArrayList()
    private lateinit var recyclerViews: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = AppPreferences(this)
        deviceStr = intent.getStringExtra("device").toString().uppercase(Locale.getDefault())
        phone = pref.getUserData().phone
        recyclerViews = findViewById(R.id.recyclerView)
        recyclerViews.visibility = GONE
        waiting.visibility = VISIBLE
        recyclerViews.layoutManager = (LinearLayoutManager(this))
        back.setOnClickListener {
            onBackPressed()
        }

    }

    private fun loadData() {
        val adapter = ResultAdapter(results)
        if (results.isNotEmpty()) {
            recyclerViews.visibility = VISIBLE
            waiting.visibility = GONE
        }
        Log.d(TAG, results.toString())
        recyclerViews.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        Log.d("amoko", "{\"device\": \"$deviceStr\", \"phone\": \"$phone\"}")
        initWebSocket()
    }

    override fun onPause() {
        super.onPause()
        webSocketClient.close()
    }

    private fun initWebSocket() {
        val coinbaseUri = URI(WEB_SOCKET_URL)
        createWebSocketClient(coinbaseUri)
    }

    private fun createWebSocketClient(coinbaseUri: URI?) {
        webSocketClient = object : WebSocketClient(coinbaseUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen")
                subscribe()
            }

            override fun onMessage(message: String?) {
//                Log.d(TAG, "onMessage: $message")
                setUpBtcPriceText(message)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose")
                onBackPressed()
                Log.d(TAG, reason.toString())
                unsubscribe()
            }

            override fun onError(ex: Exception?) {
                Log.e(TAG, "onError: ${ex?.message}")
            }

        }
        /*val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        webSocketClient.setSocketFactory(socketFactory)*/
        webSocketClient.connect()
    }

    private fun unsubscribe() {
        webSocketClient.send(
            "{\n" +
                    "    \"type\": \"unsubscribe\",\n" +
                    "    \"channels\": [\"ticker\"]\n" +
                    "}"
        )
    }

    private fun setUpBtcPriceText(message: String?) {
        message?.let {
            val data = Gson().fromJson<Any>(
                message,
                DATAVOC::class.java
            ) as DATAVOC
            results.add(data)
            runOnUiThread {
                //run your code that needs to update the UI here
                loadData()
            }
            Log.d(TAG, data.toString())
        }

    }

    private fun subscribe() {
        webSocketClient.send(
            "{\"device\": \"$deviceStr\", \"phone\": \"$phone\"}"
        )
        /*webSocketClient.send(
            "{\"device\": \"All\", \"phone\": \"+256706123303\"}"
        )*/
    }

    companion object {
        const val WEB_SOCKET_URL = "ws://137.184.24.216:7000/getupdates"
        const val TAG = "DATAVOC-Mobile"
    }
}