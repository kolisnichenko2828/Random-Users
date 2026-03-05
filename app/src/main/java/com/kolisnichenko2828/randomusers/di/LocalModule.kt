package com.kolisnichenko2828.randomusers.di

import android.content.Context
import androidx.room.Room
import com.kolisnichenko2828.randomusers.data.local.UsersDao
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}