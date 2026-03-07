package com.kolisnichenko2828.randomusers.presentation.users

interface UsersContract {
    data class State(
        val users: List<UsersUiModel> = emptyList(),
        val error: Throwable? = null,

        val isLoadingInitial: Boolean = false,
        val isLoadingNext: Boolean = false,
        val isRefreshing: Boolean = false,
    )

    sealed interface Event {
        class InitialLoad : Event
        class LoadNext : Event
        class Refresh : Event
    }
}