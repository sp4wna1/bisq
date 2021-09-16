package bisq.android.chart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetTickersUseCase {
    fun getTickers(market: String): Flow<TickerResponse>
}

class GetTickersUseCaseImpl(private val tickerRepository: TickerRepository) : GetTickersUseCase {
    override fun getTickers(market: String): Flow<TickerResponse> {
        return flow {
            emit(tickerRepository.getTickers(market))
        }
    }
}