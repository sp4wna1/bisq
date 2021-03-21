package bisq.markets.repository

import bisq.markets.service.ApiService
import bisq.markets.service.model.BidsAndAsksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class DepthRepositoryImpl(private val apiService: ApiService) : DepthRepository {

    override suspend fun fetchDepth(market: String): Flow<BidsAndAsksResponse?> = flow {
        emit(apiService.getMarket(market).bidsAndAsksResponse)
    }.flowOn(Dispatchers.IO)
}