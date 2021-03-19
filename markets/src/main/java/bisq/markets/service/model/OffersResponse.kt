package bisq.markets.service.model

import com.google.gson.annotations.SerializedName

data class OffersResponse(
    @SerializedName("btc_brl")
    val buysAndSellsResponse: BuysAndSellsResponse?
)