package bisq.android.chart

import com.google.gson.annotations.SerializedName

data class VolumeMarketResponse (
    @SerializedName("period_start") val periodStart : String,
    @SerializedName("num_trades") val numTrades : String,
    val volume : String

    )
