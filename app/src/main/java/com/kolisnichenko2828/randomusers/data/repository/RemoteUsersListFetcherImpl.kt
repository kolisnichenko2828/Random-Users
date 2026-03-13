package com.kolisnichenko2828.randomusers.data.repository

import com.kolisnichenko2828.randomusers.data.remote.UsersApi
import com.kolisnichenko2828.randomusers.data.remote.toDomain
import com.kolisnichenko2828.randomusers.di.FetcherSource
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import com.kolisnichenko2828.randomusers.domain.models.UsersModel
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
@Named(FetcherSource.REMOTE)
class RemoteUsersListFetcherImpl(
    private val api: UsersApi
) : UsersListFetcher {
    override suspend fun getUsers(
        offset: Int,
        limit: Int
    ): Result<List<UsersModel>> {
        return runCatching {
            api.getUsers().toDomain()
        }
    }
}