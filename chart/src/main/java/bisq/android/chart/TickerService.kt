package bisq.android.chart

import retrofit2.http.GET
import retrofit2.http.Query

interface TickerService {
    @GET("api/ticker")
    suspend fun getTickers(
        @Query("market") market: String
    ): TickerResponse
}