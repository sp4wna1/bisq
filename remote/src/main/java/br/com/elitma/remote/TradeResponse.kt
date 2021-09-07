package br.com.elitma.remote

import com.google.gson.annotations.SerializedName

data class TradesResponse(val trades: List<TradeResponse>)

data class TradeResponse(
    val price: String,
    val amount: String,
    val volume: String,
    @SerializedName("payment_method") val paymentMethod: String,
    @SerializedName("trade_date") val tradeDate: Long,
    val market: String,
)
