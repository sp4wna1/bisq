package bisq.local

import java.math.BigDecimal
import java.util.*

data class Offer(
    val direction: String,
    val offerDate: Date,
    val price: BigDecimal,
    val volume: BigDecimal
)