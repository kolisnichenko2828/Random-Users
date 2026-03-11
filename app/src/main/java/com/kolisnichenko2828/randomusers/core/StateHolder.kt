package com.kolisnichenko2828.randomusers.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface StateHolder<State> {
    val uiState: StateFlow<State>
    val currentState: State
    fun updateState(function: (State) -> State)
}

class StateHolderImpl<State>(
    initialState: State
) : StateHolder<State> {
    private val _uiState = MutableStateFlow(initialState)
    override val uiState: StateFlow<State> = _uiState.asStateFlow()
    override val currentState
        get() = uiState.value

    override fun updateState(function: (State) -> State) {
        _uiState.update { function(it) }
    }
}