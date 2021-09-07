package bisq.markets.repository

import br.com.elitma.remote.TradesResponse
import kotlinx.coroutines.flow.Flow

interface TradesRepository {
    suspend fun getTrades(market: String): Flow<TradesResponse>
}
