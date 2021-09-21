package bisq.android.chart

import retrofit2.http.GET
import retrofit2.http.Query

interface VolumeMarketService {

    @GET("api/volumes")
    suspend fun getVolumes(
        @Query("markets") volumes : String,
        @Query("interval") interval: String
    ): List<VolumeMarketResponse>
}


