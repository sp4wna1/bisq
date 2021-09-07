package bisq.markets.usecase

import bisq.local.Currency
import bisq.markets.usecase.presenter.Presenter
import kotlinx.coroutines.flow.Flow

internal interface CurrenciesUseCase {
    suspend fun fetchCoins(
        presenter: Presenter<List<Currency>>,
        type: String? = null,
    )

    suspend fun fetchCoins(
        type: String? = null,
    ): Flow<List<Currency>>
}