package bisq.markets.service.model

import com.google.gson.annotations.SerializedName


data class DepthResponse(
    @SerializedName("btc_brl")
    val bidsAndAsksResponse: BidsAndAsksResponse?
)