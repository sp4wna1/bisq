package bisq.markets.module

import bisq.markets.repository.DepthRepository
import bisq.markets.repository.DepthRepositoryImpl
import bisq.markets.repository.OffersRepository
import bisq.markets.repository.OffersRepositoryImpl
import bisq.markets.service.DepthService
import bisq.markets.service.OffersService
import bisq.markets.usecase.DepthUseCase
import bisq.markets.usecase.DepthUseCaseImpl
import bisq.markets.usecase.OffersUseCase
import bisq.markets.usecase.OffersUseCaseImpl
import bisq.markets.viewmodel.MarketsViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

@InternalCoroutinesApi
val marketsModule = module {
    viewModel { MarketsViewModel(get()) }

    factory<DepthUseCase> { DepthUseCaseImpl(get()) }
    factory<OffersUseCase> { OffersUseCaseImpl(get()) }

    factory<DepthRepository> { DepthRepositoryImpl(get()) }
    factory<OffersRepository> { OffersRepositoryImpl(get()) }

    factory<DepthService> { get<Retrofit>(named("RETROFIT")).create(DepthService::class.java) }
    factory<OffersService> { get<Retrofit>(named("RETROFIT")).create(OffersService::class.java) }
}