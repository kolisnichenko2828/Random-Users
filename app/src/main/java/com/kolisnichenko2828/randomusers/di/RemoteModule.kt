package com.kolisnichenko2828.randomusers.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kolisnichenko2828.randomusers.data.remote.UsersApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object FetcherSource {
    const val LOCAL = "LOCAL"
    const val REMOTE = "REMOTE"
}

@Module
class RemoteModule {
    private val BASE_URL = "https://api.api-ninjas.com/v2/"

    @Single
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Single
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val networkJson = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(networkJson.asConverterFactory(contentType))
            .build()
    }

    @Single
    fun provideUsersApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }
}