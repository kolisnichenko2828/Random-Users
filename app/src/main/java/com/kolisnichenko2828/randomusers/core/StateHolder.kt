package com.kolisnichenko2828.randomusers.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

interface StateHolder<State> {
    val uiState: StateFlow<State>
    val currentState: State
    fun setupFlow(scope: CoroutineScope, onStart: () -> Unit = {})
    fun StateHolder<State>.updateState(function: (State) -> State)
}

class StateHolderImpl<State>(
    val initialState: State
) : StateHolder<State> {
    private val _uiState = MutableStateFlow(initialState)
    override lateinit var uiState: StateFlow<State>

    override val currentState get() = uiState.value

    override fun setupFlow(scope: CoroutineScope, onStart: () -> Unit) {
        uiState = _uiState
            .onStart {
                onStart()
            }
            .stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = initialState
            )
    }

    override fun StateHolder<State>.updateState(function: (State) -> State) {
        _uiState.update { function(it) }
    }
}