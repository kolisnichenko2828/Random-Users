package com.kolisnichenko2828.randomusers.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.randomusers.core.StateHolder
import com.kolisnichenko2828.randomusers.core.StateHolderImpl
import com.kolisnichenko2828.randomusers.core.onStartCollectingState
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import com.kolisnichenko2828.randomusers.domain.mappers.toUiModels
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UsersViewModel(
    private val repository: UsersListFetcher
) : ViewModel(),
    StateHolder<UsersContract.State> by StateHolderImpl(UsersContract.State()) {

    private var job: Job? = null

    init {
        onStartCollectingState {
            setEvent(UsersContract.Event.InitialLoad())
        }
    }

    fun setEvent(event: UsersContract.Event) {
        when (event) {
            is UsersContract.Event.InitialLoad -> loadInitial()
            is UsersContract.Event.Refresh -> refresh()
            is UsersContract.Event.OnItemVisible -> checkIndex(event.index)
            is UsersContract.Event.LoadNext -> loadNext()
        }
    }

    private fun loadInitial(limit: Int = 30) {
        if (uiState.value.isLoadingInitial) return

        job?.cancel()

        job = viewModelScope.launch {
            updateState {
                it.copy(
                    error = null,
                    isLoadingInitial = true,
                    isLoadingNext = false,
                    isRefreshing = false
                )
            }

            val usersModels = repository.getUsers(
                offset = 0,
                limit = limit
            )

            usersModels.fold(
                onSuccess = { users ->
                    updateState {
                        it.copy(
                            users = users.toUiModels(),
                            error = null,
                            isLoadingInitial = false
                        )
                    }
                },
                onFailure = { exception ->
                    updateState {
                        it.copy(
                            error = exception,
                            isLoadingInitial = false
                        )
                    }
                }
            )
        }
    }

    private fun checkIndex(index: Int) {
        val threshold = uiState.value.users.size - 10
        if (index == threshold) loadNext()
    }

    private fun loadNext(limit: Int = 30) {
        if (uiState.value.isLoadingNext) return

        job?.cancel()

        job = viewModelScope.launch {
            updateState {
                it.copy(
                    error = null,
                    isLoadingNext = true
                )
            }

            val usersModels = repository.getUsers(
                offset = uiState.value.users.size,
                limit = limit
            )

            usersModels.fold(
                onSuccess = { users ->
                    updateState {
                        it.copy(
                            users = it.users + users.toUiModels(),
                            error = null,
                            isLoadingNext = false
                        )
                    }
                },
                onFailure = { exception ->
                    updateState {
                        it.copy(
                            error = exception,
                            isLoadingNext = false
                        )
                    }
                }
            )
        }
    }

    fun refresh(limit: Int = 30) {
        if (uiState.value.isRefreshing) return

        job?.cancel()

        job = viewModelScope.launch {
            updateState {
                it.copy(
                    error = null,
                    isRefreshing = true
                )
            }

            val usersModels = repository.getUsers(
                offset = 0,
                limit = limit
            )

            usersModels.fold(
                onSuccess = { users ->
                    updateState {
                        it.copy(
                            users = users.toUiModels(),
                            error = null,
                            isRefreshing = false,
                        )
                    }
                },
                onFailure = { exception ->
                    updateState {
                        it.copy(
                            error = exception,
                            isRefreshing = false
                        )
                    }
                }
            )
        }
    }
}