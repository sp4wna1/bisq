package bisq.markets.repository

import bisq.markets.service.ApiService
import br.com.elitma.remote.TradesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class TradesRepositoryImpl(private val apiService: ApiService) : TradesRepository {

    override suspend fun getTrades(market: String): Flow<TradesResponse> =
        flow { emit(apiService.getTrades(market)) }.flowOn(
            Dispatchers.IO
        )
}
