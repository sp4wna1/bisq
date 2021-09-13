package bisq.android.chart

import retrofit2.http.GET
import retrofit2.http.Query


interface ChartService {
    @GET("api/hloc")
    suspend fun getCharts(
        @Query("market") market: String,
        @Query("interval") interval: String,
    ): List<CandleResponse>
}