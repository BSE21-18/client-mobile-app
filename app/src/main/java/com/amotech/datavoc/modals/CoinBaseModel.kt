package com.amotech.datavoc.modals

data class CoinBaseModel(
    val best_ask: String,
    val best_bid: String,
    val high_24h: String,
    val last_size: String,
    val low_24h: String,
    val open_24h: String,
    val price: String,
    val product_id: String,
    val sequence: Long,
    val side: String,
    val time: String,
    val trade_id: Int,
    val type: String,
    val volume_24h: String,
    val volume_30d: String
)