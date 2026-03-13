package com.kolisnichenko2828.randomusers.data.repository

import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersCache
import com.kolisnichenko2828.randomusers.domain.mappers.toEntities
import com.kolisnichenko2828.randomusers.domain.models.UsersModel
import org.koin.core.annotation.Single

@Single
class UsersCacheImpl(
    private val database: UsersDatabase
) : UsersCache {

    override suspend fun saveUsers(users: List<UsersModel>) {
        database.usersDao().insertUsers(users.toEntities())
    }

    override suspend fun clearUsers() {
        database.usersDao().clearAll()
    }
}