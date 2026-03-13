package com.kolisnichenko2828.randomusers.data.repository

import com.kolisnichenko2828.randomusers.core.AppException
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import com.kolisnichenko2828.randomusers.data.local.toDomain
import com.kolisnichenko2828.randomusers.di.FetcherSource
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import com.kolisnichenko2828.randomusers.domain.models.UsersModel
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.concurrent.CancellationException

@Single(binds = [UsersListFetcher::class, UsersDetailsFetcher::class])
@Named(FetcherSource.LOCAL)
class LocalUsersFetcherImpl(
    private val database: UsersDatabase
) : UsersListFetcher, UsersDetailsFetcher {

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
}