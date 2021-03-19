package bisq.markets.service.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class OfferResponse(
    @SerializedName("offer_id")
    val offerId: String?,
    @SerializedName("offer_date")
    val offerDate: Long?,
    val buys: List<BigDecimal>?,
    val sells: List<BigDecimal>?,
    val direction: String?,
    @SerializedName("min_amount")
    val minAmount: BigDecimal?,
    val amount: BigDecimal?,
    val price: BigDecimal?,
    val volume: BigDecimal?,
    @SerializedName("payment_method")
    val paymentMethod: String?
)