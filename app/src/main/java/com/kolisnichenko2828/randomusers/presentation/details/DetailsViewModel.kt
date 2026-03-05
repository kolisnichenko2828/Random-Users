package com.kolisnichenko2828.randomusers.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.randomusers.data.remote.UsersRepository
import com.kolisnichenko2828.randomusers.domain.toDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: UsersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsContract.State())
    val uiState: StateFlow<DetailsContract.State> = _uiState.asStateFlow()

    fun setEvent(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.LoadUser -> loadUserDetails(event.userId)
        }
    }

    private fun loadUserDetails(userId: String) {
        if (_uiState.value.isLoading || _uiState.value.userDetails?.id == userId) return

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = repository.getUserById(userId)

            result.fold(
                onSuccess = { usersModel ->
                    val uiModel = usersModel.toDetailsUiModel()

                    _uiState.update {
                        it.copy(isLoading = false, userDetails = uiModel)
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message ?: "Loading Error")
                    }
                }
            )
        }
    }
}