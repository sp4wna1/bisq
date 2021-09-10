package bisq.android.chart

enum class CandleInterval(val value: String) {
    YEAR("1Y"),
    MONTH("1M"),
    WEEK("1W"),
    DAY("1D"),
    HALF_DAY("12H"),
    HOUR("1H"),
    HALF_HOUR("30M")
}