package com.kolisnichenko2828.randomusers.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.randomusers.core.StateHolder
import com.kolisnichenko2828.randomusers.core.StateHolderImpl
import com.kolisnichenko2828.randomusers.domain.interfaces.UsersDetailsFetcher
import com.kolisnichenko2828.randomusers.domain.mappers.toDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: UsersDetailsFetcher
) : ViewModel(), StateHolder<DetailsContract.State> by StateHolderImpl(DetailsContract.State()) {

    init {
        setupFlow(
            scope = viewModelScope
        )
    }

    fun setEvent(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.LoadUser -> loadUserDetails(event.uuid)
        }
    }

    private fun loadUserDetails(uuid: String) {
        if (currentState.userDetails?.uuid == uuid) return

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