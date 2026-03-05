package com.kolisnichenko2828.randomusers.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UsersApi {
    @GET("randomuser")
    suspend fun getUsers(
        @Header("X-Api-Key") apiKey: String = "6Bmvx3SRUVJhpIlfCEh0Id1PGmcOTXvxB2Rt31C3",
        @Query("count") count: Int = 30,
    ): List<UsersDto>
}