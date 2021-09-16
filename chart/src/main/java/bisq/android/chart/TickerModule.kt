package bisq.android.chart

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val tickerModule = module {


    factory<GetTickersUseCase> { GetTickersUseCaseImpl(get()) }

    factory<TickerRepository> { TickersRepositoryImpl(get()) }


    factory<TickerService> { get<Retrofit>(named("RETROFIT")).create(TickerService::class.java) }
}