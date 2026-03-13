package com.kolisnichenko2828.randomusers

import android.app.Application
import com.kolisnichenko2828.randomusers.di.LocalModule
import com.kolisnichenko2828.randomusers.di.RemoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

@KoinApplication
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(AppModule().module)
        }
    }
}

@Module(includes = [RemoteModule::class, LocalModule::class])
@ComponentScan("com.kolisnichenko2828.randomusers")
class AppModule