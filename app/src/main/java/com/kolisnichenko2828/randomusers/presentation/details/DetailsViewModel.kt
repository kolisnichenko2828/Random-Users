package com.kolisnichenko2828.randomusers.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.randomusers.core.StateHolder
import com.kolisnichenko2828.randomusers.core.StateHolderImpl
import com.kolisnichenko2828.randomusers.core.onStartCollectingState
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.mappers.toDetailsUiModel
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class DetailsViewModel(
    private val repository: UsersDetailsFetcher,
    @InjectedParam private val uuid: String
) : ViewModel(),
    StateHolder<DetailsContract.State> by StateHolderImpl(DetailsContract.State()) {

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