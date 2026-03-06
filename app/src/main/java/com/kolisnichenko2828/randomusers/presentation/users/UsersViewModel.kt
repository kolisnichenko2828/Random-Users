package com.kolisnichenko2828.randomusers.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.randomusers.data.remote.UsersRepository
import com.kolisnichenko2828.randomusers.domain.UsersModel
import com.kolisnichenko2828.randomusers.domain.toItemUiModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UsersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UsersContract.State())
    val uiState = _uiState.asStateFlow()

    fun getUsers(
        isRefreshing: Boolean = false,
        isNext: Boolean = false,
        limit: Int = 30
    ) {
        val currentState = _uiState.value

        if (isRefreshing && currentState.isRefreshing) return
        if (isNext && currentState.isLoadingNext) return
        if (!isRefreshing && !isNext && currentState.isLoadingInitial) return

        viewModelScope.launch {
            var usersResult: Result<List<UsersModel>>
            when {
                isRefreshing -> {
                    _uiState.update {
                        it.copy(
                            error = null,
                            isLoadingInitial = false,
                            isLoadingNext = false,
                            isRefreshing = true,
                        )
                    }
                    usersResult = repository.getUsers(
                        offset = 0,
                        limit = limit
                    )
                }
                isNext -> {
                    _uiState.update {
                        it.copy(
                            error = null,
                            isLoadingInitial = false,
                            isLoadingNext = true,
                            isRefreshing = false,
                        )
                    }
                    usersResult = repository.getUsers(
                        offset = currentState.users.size,
                        limit = 30
                    )
                }
                else -> {
                    _uiState.update {
                        it.copy(
                            error = null,
                            isLoadingInitial = true,
                            isLoadingNext = false,
                            isRefreshing = false,
                        )
                    }
                    usersResult = repository.getUsers(
                        offset = 0,
                        limit = limit
                    )
                }
            }

            usersResult.fold(
                onSuccess = { users ->
                    _uiState.update {
                        val currentUsers = if (isNext) {
                            it.users + users.toItemUiModels()
                        } else {
                            users.toItemUiModels()
                        }

                        it.copy(
                            users = currentUsers,
                            error = null,
                            isLoadingInitial = false,
                            isLoadingNext = false,
                            isRefreshing = false,
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception,
                            isLoadingInitial = false,
                            isLoadingNext = false,
                            isRefreshing = false,
                        )
                    }
                }
            )
        }
    }
}