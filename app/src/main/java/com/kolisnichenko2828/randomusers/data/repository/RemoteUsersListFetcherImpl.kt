package com.kolisnichenko2828.randomusers.data.repository

import com.kolisnichenko2828.randomusers.data.local.toDomain
import com.kolisnichenko2828.randomusers.data.remote.UsersApi
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import com.kolisnichenko2828.randomusers.data.remote.toUsersEntities
import com.kolisnichenko2828.randomusers.domain.models.UsersModel
import javax.inject.Inject

class RemoteUsersListFetcherImpl @Inject constructor(
    private val api: UsersApi
) : UsersListFetcher {
    override suspend fun getUsers(
        offset: Int,
        limit: Int
    ): Result<List<UsersModel>> {
        return runCatching {
            api.getUsers().toUsersEntities().toDomain()
        }
    }
}