package bisq.markets.repository

import bisq.markets.service.model.CurrenciesResponse
import bisq.markets.service.model.CurrencyResponse
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    suspend fun fetchCurrencies(): Flow<CurrenciesResponse>
}