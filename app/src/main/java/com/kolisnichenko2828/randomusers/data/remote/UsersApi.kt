package com.kolisnichenko2828.randomusers.data.remote

import com.kolisnichenko2828.randomusers.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UsersApi {
    @GET("randomuser")
    suspend fun getUsers(
        @Header("X-Api-Key") apiKey: String = BuildConfig.RANDOM_API,
        @Query("count") count: Int = 30,
    ): List<UsersDto>
}