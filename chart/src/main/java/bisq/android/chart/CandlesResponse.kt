package bisq.android.chart

data class CandleResponse(
    val period_start: Long,
    val open: String,
    val close: String,
    val high: String,
    val low: String,
    val avg: String,
    val volume_right: String,
    val volume_left: String,
)