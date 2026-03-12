package com.kolisnichenko2828.randomusers.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.randomusers.core.StateHolder
import com.kolisnichenko2828.randomusers.core.StateHolderImpl
import com.kolisnichenko2828.randomusers.core.onStartCollectingState
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.mappers.toDetailsUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    private val repository: UsersDetailsFetcher,
    @Assisted val uuid: String
) : ViewModel(),
    StateHolder<DetailsContract.State> by StateHolderImpl(DetailsContract.State()) {

    @AssistedFactory
    interface Factory {
        fun create(uuid: String): DetailsViewModel
    }

    init {
        onStartCollectingState {
            setEvent(DetailsContract.Event.LoadUser(uuid))
        }
    }

    fun setEvent(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.LoadUser -> loadUserDetails(event.uuid)
        }
    }

    private fun loadUserDetails(uuid: String) {
        if (uiState.value.userDetails?.uuid == uuid) return

        updateState { it.copy(error = null) }

        viewModelScope.launch {
            val result = repository.getUser(uuid)

            result.fold(
                onSuccess = { usersModel ->
                    val uiModel = usersModel.toDetailsUiModel()

                    updateState {
                        it.copy(
                            userDetails = uiModel
                        )
                    }
                },
                onFailure = { error ->
                    updateState {
                        it.copy(
                            error = error
                        )
                    }
                }
            )
        }
    }
}