package bisq.markets.repository

import bisq.markets.service.DepthService
import bisq.markets.service.model.BidsAndAsksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class DepthRepositoryImpl(private val depthService: DepthService) : DepthRepository {

    override suspend fun fetchDepth(market: String): Flow<BidsAndAsksResponse?> = flow {
        emit(depthService.getMarket(market).bidsAndAsksResponse)
    }.flowOn(Dispatchers.IO)
}