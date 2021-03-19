package bisq.markets.repository

import bisq.markets.service.OffersService
import bisq.markets.service.model.OffersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class OffersRepositoryImpl(private val offersService: OffersService) : OffersRepository {

    override suspend fun fetchOffers(market: String): Flow<OffersResponse> =
        flow { emit(offersService.getOffers(market)) }.flowOn(Dispatchers.IO)
}