package bisq.android.chart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetChartsUseCase {
    fun getCandles(market: String, interval: CandleInterval): Flow<List<CandleResponse>>
}

class GetChartsUseCaseImpl(private val candlesRepository: CandlesRepository) : GetChartsUseCase {
    override fun getCandles(market: String, interval: CandleInterval): Flow<List<CandleResponse>> {
        return flow {
            emit(candlesRepository.getCharts(market, interval))
        }
    }
}