package com.kolisnichenko2828.randomusers.di

import android.content.Context
import androidx.room.Room
import com.kolisnichenko2828.randomusers.data.local.UsersDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class LocalModule {

    @Single
    fun provideUsersDatabase(context: Context): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            "users"
        ).build()
    }
}