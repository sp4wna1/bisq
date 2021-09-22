package bisq.android.chart

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val chartModule = module {
    viewModel { ChartsViewModel(get(),get(),get()) }

    factory<GetChartsUseCase> { GetChartsUseCaseImpl(get()) }

    factory<CandlesRepository> { CandlesRepositoryImpl(get()) }


    factory<ChartService> { get<Retrofit>(named("RETROFIT")).create(ChartService::class.java) }
}