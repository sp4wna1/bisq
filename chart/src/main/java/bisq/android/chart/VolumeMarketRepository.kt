package bisq.android.chart


interface VolumeMarketRepository {
    suspend fun getVolumes(market: String, interval: String): List<VolumeMarketResponse>
}

class VolumeMarketRepositoryImpl(private val volumeMarketService: VolumeMarketService) : VolumeMarketRepository {
    override suspend fun getVolumes(market: String, interval: String): List<VolumeMarketResponse> =
        volumeMarketService.getVolumes(market, interval)
}
