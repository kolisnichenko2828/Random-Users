package com.kolisnichenko2828.randomusers

import android.app.Application
import com.kolisnichenko2828.randomusers.di.localModule
import com.kolisnichenko2828.randomusers.di.presentationModule
import com.kolisnichenko2828.randomusers.di.remoteModule
import com.kolisnichenko2828.randomusers.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(localModule, remoteModule, repositoryModule, presentationModule)
        }
    }
}