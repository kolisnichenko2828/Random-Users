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

class UsersPagingSource(
    private val api: UsersApi,
    private val database: UsersDatabase
) : PagingSource<Int, UsersModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersModel> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        return try {
            val response = api.getUsers(count = limit)

            val entities = response.toUserEntities()
            if (offset == 0) database.usersDao().clearAll()
            database.usersDao().insertUsers(entities)

            LoadResult.Page(
                data = entities.toDomain(),
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (response.isEmpty()) null else offset + limit
            )
        } catch (e: Exception) {
            val appException = when (e) {
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

            val localUsers = database.usersDao().getUsers(
                limit = limit,
                offset = offset
            )

            if (localUsers.isNotEmpty()) {
                LoadResult.Page(
                    data = localUsers.toDomain(),
                    prevKey = if (offset == 0) null else offset - limit,
                    nextKey = if (localUsers.size < limit) null else offset + limit
                )
            } else {
                LoadResult.Error(appException)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UsersModel>): Int? {
        return null
    }
}