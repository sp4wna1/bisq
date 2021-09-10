package bisq.android.chart

interface CandlesRepository {
    suspend fun getCharts(market: String, interval: CandleInterval): List<CandleResponse>
}

class CandlesRepositoryImpl(private val chartService: ChartService) : CandlesRepository {
    override suspend fun getCharts(market: String, interval: CandleInterval): List<CandleResponse> =
        chartService.getCharts(market, interval.name)
}