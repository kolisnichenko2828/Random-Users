package com.kolisnichenko2828.randomusers.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.randomusers.core.StateHolder
import com.kolisnichenko2828.randomusers.core.StateHolderImpl
import com.kolisnichenko2828.randomusers.core.onStartCollectingState
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.mappers.toDetailsUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: UsersDetailsFetcher,
    private val uuid: String
) : ViewModel(),
    StateHolder<DetailsContract.State> by StateHolderImpl(DetailsContract.State()) {

    var job: Job? = null

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

        job?.cancel()

        job = viewModelScope.launch {
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