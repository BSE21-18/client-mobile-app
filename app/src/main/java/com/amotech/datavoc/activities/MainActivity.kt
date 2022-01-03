package com.amotech.datavoc.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amotech.datavoc.R
import com.amotech.datavoc.adapter.ResultAdapter
import com.amotech.datavoc.modals.CoinBaseModel
import com.amotech.datavoc.modals.Result
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory

class MainActivity : AppCompatActivity() {
    private lateinit var webSocketClient: WebSocketClient
    private var results: MutableList<Result> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = (LinearLayoutManager(this))
        results.add(
            Result(
                "20/12/2021",
                "18:06",
                "DV20794",
                "Late Blight",
                1,
                "Do not disturb me please"
            )
        )
        results.add(
            Result(
                "20/12/2021",
                "18:06",
                "DV20794",
                "Late Blight",
                2,
                "Do not disturb me please"
            )
        )

        val adapter = ResultAdapter(results)
        recyclerView.adapter = adapter


        back.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onResume() {
        super.onResume()
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
                Log.d(TAG, "onMessage: $message")
                setUpBtcPriceText(message)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose")
                unsubscribe()
            }

            override fun onError(ex: Exception?) {
                Log.e(TAG, "onError: ${ex?.message}")
            }

        }
        val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        webSocketClient.setSocketFactory(socketFactory)
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
                CoinBaseModel::class.java
            ) as CoinBaseModel
            if (data.low_24h.isNotEmpty())
                Log.d("amokoset", data.toString())
        }
    }

    private fun subscribe() {
        webSocketClient.send(
            "{\n" +
                    "    \"type\": \"subscribe\",\n" +
                    "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BTC-EUR\"] }]\n" +
                    "}"
        )
    }

    companion object {
        const val WEB_SOCKET_URL = "wss://ws-feed.pro.coinbase.com"
        const val TAG = "Coinbase"
    }
}