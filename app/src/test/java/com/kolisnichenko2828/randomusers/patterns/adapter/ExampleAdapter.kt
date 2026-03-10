package com.kolisnichenko2828.randomusers.patterns.adapter

import org.junit.Test

private interface UsersRepository {
    fun getUsers(count: Int): List<String>
}

private class UsersRepositoryImpl : UsersRepository {
    override fun getUsers(count: Int): List<String> {
        val users: MutableList<String> = mutableListOf()
        for (i in 0 until count) {
            users.add("User $i")
        }
        return users
    }
}

private data class ExternalModel(
    val id: Int,
    val name: String,
    val username: String
)

private class ExternalSdk {
    fun getUser(id: Int): ExternalModel {
        return ExternalModel(
            id = id * 123,
            name = "User $id",
            username = "username_$id"
        )
    }
}

private class ExternalSdkAdapter(
    private val externalSdk: ExternalSdk
) : UsersRepository {
    override fun getUsers(count: Int): List<String> {
        val users: MutableList<String> = mutableListOf()
        for (i in 0 until count) {
            users.add(externalSdk.getUser(i).name)
        }
        return users
    }
}

class AdapterTest {
    @Test
    fun main() {
        // without adapter
        val repository1: UsersRepository = UsersRepositoryImpl()
        val users1 = repository1.getUsers(5)
        for (user in users1) println(user)
        // with adapter
        val repository2: UsersRepository = ExternalSdkAdapter(ExternalSdk())
        val users2 = repository2.getUsers(5)
        for (user in users2) println(user)
    }
}