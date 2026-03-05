package com.kolisnichenko2828.randomusers.data.remote

import com.kolisnichenko2828.randomusers.data.local.UsersDao
import com.kolisnichenko2828.randomusers.data.local.toDomain
import com.kolisnichenko2828.randomusers.domain.UsersModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val api: UsersApi,
    private val dao: UsersDao
) {
    suspend fun getUsers(): Result<List<UsersModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val userEntities = api.getUsers().toUserEntities()

                dao.clearAll()
                dao.insertUsers(userEntities)

                Result.success(userEntities.toDomain())
            } catch (e: Exception) {
                val cachedEntities = dao.getAllUsers()

                if (cachedEntities.isNotEmpty()) {
                    Result.success(cachedEntities.toDomain())
                } else {
                    Result.failure(e)
                }
            }
        }
    }

    suspend fun getUserById(id: String): Result<UsersModel> {
        return withContext(Dispatchers.IO) {
            val entity = dao.getUserById(id)

            if (entity != null) {
                Result.success(entity.toDomain())
            } else {
                Result.failure(Exception("Користувача не знайдено в базі"))
            }
        }
    }
}