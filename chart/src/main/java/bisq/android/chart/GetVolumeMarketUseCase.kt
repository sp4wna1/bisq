package bisq.android.chart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetVolumeMarketUseCase {
    fun getVolumes(market: String, interval: String): Flow<List<VolumeMarketResponse>>
}

class GetVolumeMarketUseCaseImpl(private val volumeMarketRepository: VolumeMarketRepository) : GetVolumeMarketUseCase {
    override fun getVolumes(market: String, interval: String): Flow<List<VolumeMarketResponse>> {
        return flow {
            emit(volumeMarketRepository.getVolumes(market, interval))
        }
    }
}