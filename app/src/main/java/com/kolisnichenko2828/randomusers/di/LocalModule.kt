package com.kolisnichenko2828.randomusers.di

import androidx.room.Room
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import com.kolisnichenko2828.randomusers.data.repository.LocalUsersFetcherImpl
import com.kolisnichenko2828.randomusers.data.repository.UsersCacheImpl
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersCache
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

val localModule = module {
    single<UsersDatabase> {
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            "users"
        ).build()
    }

    single<UsersCache> {
        UsersCacheImpl(
            database = get()
        )
    }

    single(named(FetcherSource.LOCAL)) {
        LocalUsersFetcherImpl(get())
    } binds arrayOf(
        UsersListFetcher::class,
        UsersDetailsFetcher::class
    )
}