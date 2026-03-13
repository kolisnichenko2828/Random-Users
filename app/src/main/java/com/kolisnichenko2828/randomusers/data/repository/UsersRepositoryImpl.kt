package com.kolisnichenko2828.randomusers.data.repository

import com.kolisnichenko2828.randomusers.core.AppException
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersCache
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import com.kolisnichenko2828.randomusers.domain.models.UsersModel
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

class UsersRepositoryImpl(
    private val remoteFetcher: UsersListFetcher,
    private val localFetcher: UsersListFetcher,
    private val detailsFetcher: UsersDetailsFetcher,
    private val usersCache: UsersCache
) : UsersListFetcher, UsersDetailsFetcher {

    override suspend fun getUsers(
        offset: Int,
        limit: Int
    ): Result<List<UsersModel>> {
        val remoteModels = remoteFetcher.getUsers(offset, limit)

        remoteModels.fold(
            onSuccess = { currentRemoteModels ->
                if (offset == 0) usersCache.clearUsers()
                usersCache.saveUsers(currentRemoteModels)
                return Result.success(currentRemoteModels)
            },
            onFailure = { remoteException ->
                if (remoteException is CancellationException) throw remoteException
                val localModels = localFetcher.getUsers(offset, limit)
                localModels.fold(
                    onSuccess = { currentLocalModels ->
                        if (currentLocalModels.isNotEmpty()) {
                            return Result.success(currentLocalModels)
                        } else {
                            return Result.failure(getRemoteException(remoteException))
                        }
                    },
                    onFailure = { localException ->
                        if (localException is CancellationException) throw localException
                        return Result.failure(AppException.DatabaseError())
                    }
                )
            }
        )
    }

    override suspend fun getUser(uuid: String): Result<UsersModel> {
        return detailsFetcher.getUser(uuid)
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