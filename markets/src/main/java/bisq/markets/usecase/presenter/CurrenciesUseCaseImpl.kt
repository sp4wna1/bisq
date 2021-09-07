package bisq.markets.usecase.presenter

import bisq.local.Currency
import bisq.markets.repository.CurrenciesRepository
import bisq.markets.usecase.CurrenciesUseCase
import kotlinx.coroutines.flow.*

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

    override suspend fun fetchCoins(): Flow<List<Currency>> =
        currenciesRepository.fetchCurrencies().map {
            it.currencies.map { response ->
                Currency(
                    response.code,
                    response.name,
                    response.precision,
                    response.type
                )
            }
        }
}