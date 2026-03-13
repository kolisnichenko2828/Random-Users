package com.kolisnichenko2828.randomusers.di

import com.kolisnichenko2828.randomusers.data.repository.UsersRepositoryImpl
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

enum class FetcherSource {
    LOCAL,
    REMOTE
}

val repositoryModule = module {
    single {
        UsersRepositoryImpl(
            remoteFetcher = get(named(FetcherSource.REMOTE)),
            localFetcher = get(named(FetcherSource.LOCAL)),
            detailsFetcher = get(named(FetcherSource.LOCAL)),
            usersCache = get()
        )
    } binds arrayOf(
        UsersListFetcher::class,
        UsersDetailsFetcher::class
    )
}