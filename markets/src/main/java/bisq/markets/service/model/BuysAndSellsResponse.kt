package bisq.markets.service.model

data class BuysAndSellsResponse(
    val buys: List<OfferResponse>?,
    val sells: List<OfferResponse>?
)