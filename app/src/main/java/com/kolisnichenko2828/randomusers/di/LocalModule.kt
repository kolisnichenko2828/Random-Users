package com.kolisnichenko2828.randomusers.di

import android.content.Context
import androidx.room.Room
import com.kolisnichenko2828.randomusers.data.local.UsersDao
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import com.kolisnichenko2828.randomusers.data.repository.LocalUsersFetcherImpl
import com.kolisnichenko2828.randomusers.data.repository.UsersCacheImpl
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersCache
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import dagger.Binds
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
abstract class LocalModule {

    @Binds
    @LocalFetcher
    abstract fun bindLocalUsersListFetcher(impl: LocalUsersFetcherImpl): UsersListFetcher

    @Binds
    @LocalFetcher
    abstract fun bindUserDetailsFetcher(impl: LocalUsersFetcherImpl): UsersDetailsFetcher

    @Binds
    abstract fun bindUsersCache(impl: UsersCacheImpl): UsersCache

    companion object {
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
    }
}