package com.kolisnichenko2828.randomusers.di

import android.content.Context
import com.kolisnichenko2828.randomusers.AppModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class KoinGraphTest : KoinTest {
    @Test
    fun checkKoinModules() {
        val allModules = AppModule().module

        allModules.verify(
            extraTypes = listOf(
                Context::class
            )
        )
    }
}