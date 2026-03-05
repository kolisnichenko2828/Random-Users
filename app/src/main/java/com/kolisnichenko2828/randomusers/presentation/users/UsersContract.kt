package com.kolisnichenko2828.randomusers.presentation.users

interface UsersContract {
    data class State(
        val isLoading: Boolean = false,
        val users: List<UsersUiModel> = emptyList(),
        val error: String? = null
    )

    sealed interface Event {
        data object LoadUsers : Event
    }
}