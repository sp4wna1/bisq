package bisq.markets.service.model

import java.math.BigDecimal

data class BidsAndAsksResponse(
    val buys: List<BigDecimal>?,
    val sells: List<BigDecimal>?
)