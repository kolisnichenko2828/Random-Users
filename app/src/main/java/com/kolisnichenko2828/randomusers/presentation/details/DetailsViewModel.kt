package com.kolisnichenko2828.randomusers.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.mappers.toDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: UsersDetailsFetcher
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsContract.State())
    val uiState: StateFlow<DetailsContract.State> = _uiState.asStateFlow()

    fun setEvent(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.LoadUser -> loadUserDetails(event.uuid)
        }
    }

    private fun loadUserDetails(uuid: String) {
        if (_uiState.value.userDetails?.uuid == uuid) return

        _uiState.update { it.copy(error = null) }

        viewModelScope.launch {
            val result = repository.getUser(uuid)

            result.fold(
                onSuccess = { usersModel ->
                    val uiModel = usersModel.toDetailsUiModel()

                    _uiState.update {
                        it.copy(
                            userDetails = uiModel
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            error = error
                        )
                    }
                }
            )
        }
    }
}