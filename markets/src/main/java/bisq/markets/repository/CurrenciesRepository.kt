package bisq.markets.repository

import br.com.elitma.remote.CurrenciesResponse
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    suspend fun fetchCurrencies(type: String?): Flow<CurrenciesResponse>
}