package bisq.markets.module

import bisq.markets.repository.*
import bisq.markets.service.ApiService
import bisq.markets.usecase.*
import bisq.markets.usecase.presenter.CurrenciesUseCaseImpl
import bisq.markets.viewmodel.CurrencyViewModel
import bisq.markets.viewmodel.MarketsViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

@InternalCoroutinesApi
val marketsModule = module {
    viewModel { MarketsViewModel(get(), get()) }
    viewModel { CurrencyViewModel(get()) }

    factory<DepthUseCase> { DepthUseCaseImpl(get()) }
    factory<OffersUseCase> { OffersUseCaseImpl(get()) }
    factory<CurrenciesUseCase> { CurrenciesUseCaseImpl(get()) }

    factory<DepthRepository> { DepthRepositoryImpl(get()) }
    factory<OffersRepository> { OffersRepositoryImpl(get()) }
    factory<CurrenciesRepository> { CurrenciesRepositoryImpl(get()) }

    factory<ApiService> { get<Retrofit>(named("RETROFIT")).create(ApiService::class.java) }
}