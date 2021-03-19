package network.bisq

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun BigDecimal.toCurrency(maxFractions: Int, currency: Currency): String =
    NumberFormat.getCurrencyInstance().let {
        it.maximumFractionDigits = maxFractions
        it.currency = currency
        it.format(this@toCurrency.toDouble())
    }


fun BigDecimal.toNumber(maxFractions: Int): String =
    NumberFormat.getInstance().let {
        it.maximumFractionDigits = maxFractions
        it.format(this@toNumber.toDouble())
    }