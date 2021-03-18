package network.bisq.module

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO Move this to properties
private const val BASE_URL = "https://bisq.markets/"

val appModule = module {
    single(named("RETROFIT")) {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}