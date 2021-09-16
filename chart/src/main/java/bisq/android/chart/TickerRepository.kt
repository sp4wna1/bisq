package bisq.android.chart

interface TickerRepository {
    suspend fun getTickers(market: String): TickerResponse
}

class TickersRepositoryImpl(private val tickerService: TickerService) : TickerRepository {
    override suspend fun getTickers(market: String): TickerResponse =
        tickerService.getTickers(market)


}