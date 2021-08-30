package br.com.elitma.remote

import com.google.gson.GsonBuilder
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO Move this to properties
private const val BASE_URL = "https://bisq.markets/"

val remoteModule = module {

    single(named("GSON")) {
        GsonBuilder().registerTypeAdapter(CurrenciesResponse::class.java, CurrenciesTypeAdapter())
            .create()
    }

    single(named("RETROFIT")) {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get(named("GSON"))))
            .build()
    }
}