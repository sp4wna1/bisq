package bisq.local

import java.math.BigDecimal

data class BidsAndAsks(val bids: List<BigDecimal>, val asks: List<BigDecimal>)