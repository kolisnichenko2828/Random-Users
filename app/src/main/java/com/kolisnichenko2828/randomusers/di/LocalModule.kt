package com.kolisnichenko2828.randomusers.di

import androidx.room.Room
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import com.kolisnichenko2828.randomusers.data.repository.LocalUsersFetcherImpl
import com.kolisnichenko2828.randomusers.data.repository.UsersCacheImpl
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersCache
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val localModule = module {
    single<UsersDatabase> {
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            "users"
        ).build()
    }

    singleOf(::UsersCacheImpl) bind UsersCache::class

    singleOf(::LocalUsersFetcherImpl) {
        qualifier = named(FetcherSource.LOCAL)
        binds(
            classes = listOf(
                UsersListFetcher::class,
                UsersDetailsFetcher::class
            )
        )
    }
}