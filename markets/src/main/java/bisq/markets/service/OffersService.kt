package bisq.markets.service

import bisq.markets.service.model.OffersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OffersService {
    @GET("api/offers")
    suspend fun getOffers(@Query("market") market: String): OffersResponse
}