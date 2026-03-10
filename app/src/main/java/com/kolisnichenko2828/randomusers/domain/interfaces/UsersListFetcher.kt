package com.kolisnichenko2828.randomusers.domain.interfaces

import com.kolisnichenko2828.randomusers.domain.models.UsersModel

interface UsersListFetcher {
    suspend fun getUsers(offset: Int, limit: Int): Result<List<UsersModel>>
}