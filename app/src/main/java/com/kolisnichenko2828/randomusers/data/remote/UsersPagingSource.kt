package com.kolisnichenko2828.randomusers.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kolisnichenko2828.randomusers.core.AppException
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import com.kolisnichenko2828.randomusers.data.local.toDomain
import com.kolisnichenko2828.randomusers.domain.UsersModel
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.sql.SQLException
import java.util.concurrent.CancellationException

class UsersPagingSource(
    private val api: UsersApi,
    private val database: UsersDatabase
) : PagingSource<Int, UsersModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersModel> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        return try {
            fetchFromNetwork(offset, limit)
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> {
                    throw e
                }
                is SQLException -> {
                    LoadResult.Error(AppException.DatabaseError(e))
                }
                else -> {
                    fetchFromLocalCache(
                        offset = offset,
                        limit = limit,
                        originalError = e
                    )
                }
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UsersModel>): Int? {
        return null
    }

    private suspend fun fetchFromNetwork(
        offset: Int,
        limit: Int
    ): LoadResult.Page<Int, UsersModel> {
        val response = api.getUsers(count = limit)
        val entities = response.toUserEntities()

        if (offset == 0) database.usersDao().clearAll()
        database.usersDao().insertUsers(entities)

        return createPageResult(
            data = entities.toDomain(),
            offset = offset,
            limit = limit,
            isEndReached = response.isEmpty()
        )
    }

    private suspend fun fetchFromLocalCache(
        offset: Int,
        limit: Int,
        originalError: Exception
    ): LoadResult<Int, UsersModel> {
        return try {
            val localUsers = database.usersDao().getUsers(limit = limit, offset = offset)

            if (localUsers.isNotEmpty()) {
                createPageResult(
                    data = localUsers.toDomain(),
                    offset = offset,
                    limit = limit,
                    isEndReached = localUsers.size < limit
                )
            } else {
                LoadResult.Error(getRemoteException(originalError))
            }
        } catch (localDbException: Exception) {
            if (localDbException is CancellationException) throw localDbException
            LoadResult.Error(AppException.DatabaseError(localDbException))
        }
    }

    private fun createPageResult(
        data: List<UsersModel>,
        offset: Int,
        limit: Int,
        isEndReached: Boolean
    ): LoadResult.Page<Int, UsersModel> {
        return LoadResult.Page(
            data = data,
            prevKey = if (offset == 0) null else offset - limit,
            nextKey = if (isEndReached) null else offset + limit
        )
    }

    private fun getRemoteException(e: Exception): AppException {
        return when (e) {
            is SocketTimeoutException -> AppException.Timeout(e)
            is IOException -> AppException.NoInternetConnection(e)
            is HttpException -> {
                when (e.code()) {
                    429 -> AppException.RateLimitExceeded(e)
                    in 500..599 -> AppException.ServerError(e)
                    else -> AppException.Unknown(e)
                }
            }
            else -> AppException.Unknown(e)
        }
    }
}