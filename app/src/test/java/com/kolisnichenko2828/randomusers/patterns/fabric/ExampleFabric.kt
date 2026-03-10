package com.kolisnichenko2828.randomusers.patterns.fabric

import org.junit.Test

private class ExampleUsersApi {
    fun getName(id: Int): String {
        return "Name $id"
    }
}
private class ExampleUsersRepository(
    private val api: ExampleUsersApi
) {
    fun getName(id: Int): String {
        return api.getName(id)
    }
}

private class ExampleFabric {
    private var usersApi: ExampleUsersApi? = null

    fun provideUsersApi(): ExampleUsersApi {
        if (usersApi == null) usersApi = ExampleUsersApi()
        return usersApi!!
    }

    fun provideUsersRepository(): ExampleUsersRepository {
        return ExampleUsersRepository(
            provideUsersApi()
        )
    }
}

class FabricTest {
    @Test
    fun main() {
        val exampleFabric = ExampleFabric()
        val repository = exampleFabric.provideUsersRepository()
        val name = repository.getName(0)
        println(name)
    }
}