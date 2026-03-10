package com.kolisnichenko2828.randomusers.patterns.singleton

import org.junit.Test

private object ExampleSingleton {
    fun getUsers() {
        println("list of users")
    }
}

private class ExampleCompanion {
    companion object {
        fun getUsers() {
            println("inside companion")
        }
    }

    fun getUsers() {
        println("inside class")
    }
}

private class ExampleLazyCompanion private constructor() {
    fun getUsers() {
        println("list of users")
    }

    companion object {
        val instance: ExampleLazyCompanion by lazy {
            ExampleLazyCompanion()
        }
    }
}

class SingletonTest {
    @Test
    fun main() {
        // Singleton
        ExampleSingleton.getUsers()
        // Companion
        ExampleCompanion().getUsers()
        ExampleCompanion.getUsers()
        // LazyCompanion
        ExampleLazyCompanion.instance.getUsers()
    }
}