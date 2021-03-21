package bisq.markets.service.model

import com.google.gson.annotations.SerializedName

data class CurrenciesResponse(
    @SerializedName("BRL") val brl: CurrencyResponse,
    @SerializedName("USD") val usd: CurrencyResponse,
    @SerializedName("BTC") val btc: CurrencyResponse
)