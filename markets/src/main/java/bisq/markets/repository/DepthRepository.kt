package bisq.markets.repository

import bisq.markets.service.model.BidsAndAsksResponse
import kotlinx.coroutines.flow.Flow

interface DepthRepository {

    suspend fun fetchDepth(market: String): Flow<BidsAndAsksResponse?>
}