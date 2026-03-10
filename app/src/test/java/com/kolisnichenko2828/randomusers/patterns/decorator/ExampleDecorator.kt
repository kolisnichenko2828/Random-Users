package com.kolisnichenko2828.randomusers.patterns.decorator

import org.junit.Test

private interface ExampleUsersRepository {
    fun getUsers(count: Int): List<String>
}

private class ExampleUsersRepositoryImpl : ExampleUsersRepository {
    override fun getUsers(count: Int): List<String> {
        val users: MutableList<String> = mutableListOf()
        for (i in 0 until count) {
            users.add("User $i")
        }
        return users
    }
}

private class ExampleDecoratedRepository(
    private val repository: ExampleUsersRepository
) : ExampleUsersRepository {
    private var cachedUsers: List<String>? = null

    override fun getUsers(count: Int): List<String> {
        cachedUsers?.let { return it }

        val users = repository.getUsers(count)
        cachedUsers = users
        return users
    }
}

class DecoratorTest {
    @Test
    fun main() {
        val repository = ExampleUsersRepositoryImpl()
        // without decorator
        val users1 = repository.getUsers(5)
        for (user in users1) println(user)
        // with decorator
        val decoratedRepository = ExampleDecoratedRepository(repository)
        val users2 = decoratedRepository.getUsers(5)
        for (user in users2) println(user)
    }
}