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
            .catch { presenter.error(it) }
            .collect {
                presenter.success(it.currencies.map { response ->
                    Currency(
                        response.code,
                        response.name,
                        response.precision,
                        response.type
                    )
                }
                )
            }
    }
}