package com.kolisnichenko2828.randomusers.di

import com.kolisnichenko2828.randomusers.data.repository.UsersRepositoryImpl
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUsersListFetcher(impl: UsersRepositoryImpl): UsersListFetcher {
        return impl
    }

    @Provides
    @Singleton
    fun provideUsersDetailsFetcher(impl: UsersRepositoryImpl): UsersDetailsFetcher {
        return impl
    }
}