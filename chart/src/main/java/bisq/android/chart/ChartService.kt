package bisq.android.chart

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query



interface ChartService {
    @GET("hloc")
    fun getCharts(@Query("market") market: String):
            Call<CandleResponse>
}