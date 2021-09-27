package bisq.android.chart

import com.google.gson.annotations.SerializedName

data class TickerResponse(
    val last: String,
    val high: String,
    val low: String,
    @SerializedName("volume_left") val volumeLeft: String,
    @SerializedName("volume_right") val volumeRight: String,
    val buy: String,
    val sell: String
)
