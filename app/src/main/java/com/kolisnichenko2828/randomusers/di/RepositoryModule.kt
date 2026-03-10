package com.kolisnichenko2828.randomusers.di

import com.kolisnichenko2828.randomusers.data.repository.UsersRepositoryImpl
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUsersListFetcher(impl: UsersRepositoryImpl): UsersListFetcher

    @Binds
    @Singleton
    abstract fun bindUsersDetailsFetcher(impl: UsersRepositoryImpl): UsersDetailsFetcher
}