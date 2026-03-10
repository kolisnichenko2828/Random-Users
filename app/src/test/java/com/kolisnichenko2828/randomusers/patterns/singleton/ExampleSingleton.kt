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

private class ExampleLazyCompanion2 private constructor() {
    fun getUsers() {
        println("list of users")
    }

    companion object {
        private var instance: ExampleLazyCompanion2? = null

        fun getInstance(): ExampleLazyCompanion2 {
            instance?.let { return it }
            val tempInstance = ExampleLazyCompanion2()
            instance = tempInstance
            return tempInstance
        }
    }
}

private class ExampleLazyCompanion3 private constructor() {
    fun getUsers() {
        println("list of users")
    }

    companion object {
        @Volatile
        private var instance: ExampleLazyCompanion3? = null
        private val lock = Any()

        fun getInstance(): ExampleLazyCompanion3 {
            instance?.let { return it }

            synchronized(lock) {
                if (instance != null) {
                    return instance!!
                } else {
                    instance = ExampleLazyCompanion3()
                    return instance!!
                }
            }
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
        // LazyCompanion 2
        ExampleLazyCompanion2.getInstance().getUsers()
    }
}