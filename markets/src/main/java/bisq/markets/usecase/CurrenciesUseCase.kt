package bisq.markets.usecase

import bisq.local.Currency
import bisq.markets.usecase.presenter.Presenter

internal interface CurrenciesUseCase {
    suspend fun fetchCoins(presenter: Presenter<List<Currency>>)
}