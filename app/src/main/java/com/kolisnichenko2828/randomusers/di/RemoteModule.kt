package com.kolisnichenko2828.randomusers.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kolisnichenko2828.randomusers.data.remote.UsersApi
import com.kolisnichenko2828.randomusers.data.repository.RemoteUsersListFetcherImpl
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.api-ninjas.com/v2/"

val remoteModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        val networkJson = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(networkJson.asConverterFactory(contentType))
            .build()
    }

    single<UsersApi> {
        get<Retrofit>().create(UsersApi::class.java)
    }

    singleOf(::RemoteUsersListFetcherImpl) {
        qualifier = named(FetcherSource.REMOTE)
        bind<UsersListFetcher>()
    }
}