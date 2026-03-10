package com.kolisnichenko2828.randomusers.domain.interfaces

import com.kolisnichenko2828.randomusers.domain.models.UsersModel

interface UsersDetailsFetcher {
    suspend fun getUser(uuid: String): Result<UsersModel>
}