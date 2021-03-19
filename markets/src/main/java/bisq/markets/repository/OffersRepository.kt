package bisq.markets.repository

import bisq.markets.service.model.OffersResponse
import kotlinx.coroutines.flow.Flow

internal interface OffersRepository {
    suspend fun fetchOffers(market: String): Flow<OffersResponse>
}