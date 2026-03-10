package com.kolisnichenko2828.randomusers.data.repository

import com.kolisnichenko2828.randomusers.core.AppException
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import com.kolisnichenko2828.randomusers.data.local.toDomain
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import com.kolisnichenko2828.randomusers.domain.models.UsersModel
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersCache
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.mappers.toEntities
import java.util.concurrent.CancellationException
import javax.inject.Inject

class LocalUsersFetcherImpl @Inject constructor(
    private val database: UsersDatabase
) : UsersListFetcher, UsersDetailsFetcher, UsersCache {

    override suspend fun getUsers(
        offset: Int,
        limit: Int
    ): Result<List<UsersModel>> {
        return runCatching {
            database.usersDao().getUsers(offset, limit).toDomain()
        }
    }

    override suspend fun getUser(uuid: String): Result<UsersModel> {
        val usersEntity = runCatching { database.usersDao().getUserById(uuid) }

        usersEntity.fold(
            onSuccess = { entity ->
                if (entity != null) {
                    return Result.success(entity.toDomain())
                } else {
                    return Result.failure(AppException.DatabaseError())
                }
            },
            onFailure = { localException ->
                if (localException is CancellationException) throw localException
                return Result.failure(AppException.DatabaseError())
            }
        )
    }

    override suspend fun saveUsers(users: List<UsersModel>) {
        database.usersDao().insertUsers(users.toEntities())
    }

    override suspend fun clearUsers() {
        database.usersDao().clearAll()
    }
}