package bisq.local

data class Currency(
    val code: String,
    val name: String,
    val precision: Int,
    val type: String
)