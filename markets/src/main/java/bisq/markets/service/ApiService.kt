package bisq.markets.service

import bisq.markets.service.model.DepthResponse
import bisq.markets.service.model.OffersResponse
import br.com.elitma.remote.CurrenciesResponse
import br.com.elitma.remote.TradesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/depth")
    suspend fun getMarket(
        @Query("market") market: String,
    ): DepthResponse

    @GET("api/offers")
    suspend fun getOffers(
        @Query("market") market: String,
    ): OffersResponse

    @GET("api/currencies")
    suspend fun getCurrencies(
        @Query("type") type: String?,
    ): CurrenciesResponse

    @GET("bisq/api/markets/trades")
    suspend fun getTrades(
        @Query("market") market: String,
    ): TradesResponse
}