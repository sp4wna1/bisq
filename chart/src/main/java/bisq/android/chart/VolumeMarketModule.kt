package bisq.android.chart

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val volumeMarketModule = module {


    factory<GetVolumeMarketUseCase> { GetVolumeMarketUseCaseImpl(get()) }

    factory<VolumeMarketRepository> { VolumeMarketRepositoryImpl(get()) }


    factory<VolumeMarketService> { get<Retrofit>(named("RETROFIT")).create(VolumeMarketService::class.java) }
}