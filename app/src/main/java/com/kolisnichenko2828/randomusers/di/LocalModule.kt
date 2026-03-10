package com.kolisnichenko2828.randomusers.di

import android.content.Context
import androidx.room.Room
import com.kolisnichenko2828.randomusers.data.local.UsersDao
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import com.kolisnichenko2828.randomusers.data.repository.LocalUsersFetcherImpl
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersCache
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalFetcher

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideUsersDatabase(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            "users"
        ).build()
    }

    @Provides
    fun provideUsersDao(database: UsersDatabase): UsersDao {
        return database.usersDao()
    }

    @Provides
    @Singleton
    fun provideLocalUsersFetcherImpl(database: UsersDatabase): LocalUsersFetcherImpl {
        return LocalUsersFetcherImpl(database)
    }

    @Provides
    @LocalFetcher
    fun provideLocalUsersListFetcher(impl: LocalUsersFetcherImpl): UsersListFetcher {
        return impl
    }

    @Provides
    @LocalFetcher
    fun provideUserDetailsFetcher(impl: LocalUsersFetcherImpl): UsersDetailsFetcher {
        return impl
    }

    @Provides
    fun provideUsersCache(impl: LocalUsersFetcherImpl): UsersCache {
        return impl
    }
}