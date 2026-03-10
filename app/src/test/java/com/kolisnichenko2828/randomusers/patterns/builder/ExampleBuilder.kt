package com.kolisnichenko2828.randomusers.patterns.builder

import org.junit.Test

private class ExampleUser private constructor(
    private val name: String,
    private val age: Int,
    private val email: String
) {
    class Builder {
        private var name: String = ""
        private var age: Int = 0
        private var email: String = ""

        fun addName(name: String): Builder {
            if (name.isNotBlank()) this.name = name
            return this
        }

        fun addAge(age: Int): Builder {
            if (age >= 0) this.age = age
            return this
        }

        fun addEmail(email: String): Builder {
            if (email.isNotBlank()) this.email = email
            return this
        }

        fun build(): ExampleUser {
            return ExampleUser(
                name,
                age,
                email
            )
        }
    }

    fun getName(): String {
        return this.name
    }
}

class BuilderTest {
    @Test
    fun main() {
        val user = ExampleUser.Builder()
            .addName("Some Name")
            .addAge(31)
            .addEmail("some.email@gmail.com")
            .build()
        println(user.getName())
    }
}