package bisq.markets.usecase

internal interface GetTradesUseCase {
    suspend fun fetchTades()
}