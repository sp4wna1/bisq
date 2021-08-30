package bisq.markets.repository

import bisq.markets.service.ApiService
import br.com.elitma.remote.CurrenciesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class CurrenciesRepositoryImpl(private val apiService: ApiService) : CurrenciesRepository {

    override suspend fun fetchCurrencies(): Flow<CurrenciesResponse> =
        flow { emit(apiService.getCurrencies()) }.flowOn(
            Dispatchers.IO
        )

}