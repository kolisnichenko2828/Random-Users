package com.kolisnichenko2828.randomusers.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.randomusers.data.remote.UsersRepository
import com.kolisnichenko2828.randomusers.domain.toUsersUiModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UsersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UsersContract.State())
    val uiState: StateFlow<UsersContract.State> = _uiState
        .onStart {
            if (_uiState.value.users.isEmpty()) {
                loadUsers()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = UsersContract.State()
        )

    fun setEvent(event: UsersContract.Event) {
        when (event) {
            is UsersContract.Event.LoadUsers -> loadUsers()
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = repository.getUsers()

            result.fold(
                onSuccess = { usersModels ->
                    _uiState.update {
                        val usersUiModel = usersModels.toUsersUiModels()
                        it.copy(isLoading = false, users = usersUiModel)
                    }
                },
                onFailure = { error ->
                    val errorMessage = error.message ?: "Unknown Error"
                    _uiState.update {
                        it.copy(isLoading = false, error = errorMessage)
                    }
                }
            )
        }
    }
}