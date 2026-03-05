package com.kolisnichenko2828.randomusers.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kolisnichenko2828.randomusers.core.AppException
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import com.kolisnichenko2828.randomusers.data.local.toDomain
import com.kolisnichenko2828.randomusers.domain.UsersModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val api: UsersApi,
    private val database: UsersDatabase,
) {
    fun getUsers(): Flow<PagingData<UsersModel>> {
        return Pager(
            config = PagingConfig(
                prefetchDistance = 5,
                pageSize = 30,
                initialLoadSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingSource(api, database) }
        ).flow
    }

    suspend fun getUserById(uuid: String): Result<UsersModel> {
        return withContext(Dispatchers.IO) {
            val entity = database.usersDao().getUserById(uuid)

            if (entity != null) {
                Result.success(entity.toDomain())
            } else {
                Result.failure(AppException.Unknown())
            }
        }
    }
}