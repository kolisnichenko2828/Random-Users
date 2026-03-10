package com.kolisnichenko2828.randomusers.domain.interfaces

import com.kolisnichenko2828.randomusers.domain.models.UsersModel

interface UsersCache {
    suspend fun saveUsers(users: List<UsersModel>)
    suspend fun clearUsers()
}