package com.kolisnichenko2828.randomusers.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.randomusers.core.StateHolder
import com.kolisnichenko2828.randomusers.core.StateHolderImpl
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersListFetcher
import com.kolisnichenko2828.randomusers.domain.mappers.toUiModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UsersListFetcher
) : ViewModel(), StateHolder<UsersContract.State> by StateHolderImpl(UsersContract.State()) {

    fun setEvent(event: UsersContract.Event) {
        when (event) {
            is UsersContract.Event.InitialLoad -> loadInitial()
            is UsersContract.Event.Refresh -> refresh()
            is UsersContract.Event.OnItemVisible -> checkIndex(event.index)
            is UsersContract.Event.LoadNext -> loadNext()
        }
    }

    private fun loadInitial(limit: Int = 30) {
        if (currentState.isLoadingInitial) return

        viewModelScope.launch {
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
        val threshold = currentState.users.size - 10
        if (index == threshold) loadNext()
    }

    private fun loadNext(limit: Int = 30) {
        if (currentState.isLoadingNext) return

        viewModelScope.launch {
            updateState {
                it.copy(
                    error = null,
                    isLoadingNext = true
                )
            }

            val usersModels = repository.getUsers(
                offset = currentState.users.size,
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
        if (currentState.isRefreshing) return

        viewModelScope.launch {
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