package bisq.markets.usecase.presenter

import bisq.local.Currency
import bisq.markets.repository.CurrenciesRepository
import bisq.markets.usecase.CurrenciesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

internal class CurrenciesUseCaseImpl(private val currenciesRepository: CurrenciesRepository) :
    CurrenciesUseCase {
    override suspend fun fetchCoins(presenter: Presenter<List<Currency>>) {
        currenciesRepository.fetchCurrencies()
            .onStart {
                presenter.loading()
            }
            .catch { presenter.error() }
            .collect {
                presenter.success(
                    listOf(
                        Currency(
                            it.brl.code ?: "",
                            it.brl.name ?: "",
                            it.brl.precision ?: 0,
                            it.brl.type ?: ""
                        ),
                        Currency(
                            it.usd.code ?: "",
                            it.usd.name ?: "",
                            it.usd.precision ?: 0,
                            it.usd.type ?: ""
                        )
                        , Currency(
                            it.btc.code ?: "",
                            it.btc.name ?: "",
                            it.btc.precision ?: 0,
                            it.btc.type ?: ""
                        )
                    )
                )
            }
    }

}