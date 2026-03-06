package com.kolisnichenko2828.randomusers.data.remote

import com.kolisnichenko2828.randomusers.core.AppException
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import com.kolisnichenko2828.randomusers.data.local.toDomain
import com.kolisnichenko2828.randomusers.domain.UsersModel
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val api: UsersApi,
    private val database: UsersDatabase
) {
    suspend fun getUsers(offset: Int, limit: Int): Result<List<UsersModel>> {
        val usersDtos = runCatching { api.getUsers(count = limit) }

        usersDtos.fold(
            onSuccess = { dtos ->
                if (offset == 0) database.usersDao().clearAll()
                val usersEntities = dtos.toUsersEntities()
                database.usersDao().insertUsers(usersEntities)
                return Result.success(database.usersDao().getUsers(offset, limit).toDomain())
            },
            onFailure = { remoteException ->
                if (remoteException is CancellationException) throw remoteException
                val usersEntities = runCatching { database.usersDao().getUsers(offset, limit) }
                usersEntities.fold(
                    onSuccess = { entities ->
                        if (entities.isNotEmpty()) {
                            return Result.success(entities.toDomain())
                        } else {
                            return Result.failure(getRemoteException(remoteException))
                        }
                    },
                    onFailure = {
                        if (remoteException is CancellationException) throw remoteException
                        val currentException = AppException.DatabaseError()
                        return Result.failure(currentException)
                    }
                )
            }
        )
    }

    suspend fun getUserById(uuid: String): Result<UsersModel> {
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

    private fun getRemoteException(t: Throwable): AppException {
        return when (t) {
            is SocketTimeoutException -> AppException.Timeout()
            is IOException -> AppException.NoInternetConnection()
            is HttpException -> {
                when (t.code()) {
                    429 -> AppException.RateLimitExceeded()
                    in 500..599 -> AppException.ServerError()
                    else -> AppException.Unknown()
                }
            }
            else -> AppException.Unknown()
        }
    }
}