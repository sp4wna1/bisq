package bisq.markets.service

import bisq.markets.service.model.DepthResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface DepthService {
    @GET("api/depth")
    suspend fun getMarket(@Query("market") market: String): DepthResponse
}