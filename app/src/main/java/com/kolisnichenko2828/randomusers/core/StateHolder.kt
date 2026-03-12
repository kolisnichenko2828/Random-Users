package com.kolisnichenko2828.randomusers.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

interface StateHolder<State> {
    val uiState: StateFlow<State>
    fun setupFlow(scope: CoroutineScope, onStart: () -> Unit = {})
    fun StateHolder<State>.updateState(function: (State) -> State)
}

class StateHolderImpl<State>(
    val initialState: State,
) : StateHolder<State> {

    private val _uiState = MutableStateFlow(initialState)
    private var scope: CoroutineScope? = null
    private var onStart: (() -> Unit)? = null
    override val uiState: StateFlow<State> by lazy {
        scope?.let { scope ->
            onStart?.let { onStart ->
                _uiState
                    .onStart {
                        onStart()
                    }.stateIn(
                        scope = scope,
                        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                        initialValue = initialState,
                    )
            }
        } ?: _uiState.asStateFlow()
    }

    override fun setupFlow(scope: CoroutineScope, onStart: () -> Unit) {
        this.scope = scope
        this.onStart = onStart
    }

    override fun StateHolder<State>.updateState(function: (State) -> State) {
        _uiState.update(function)
    }
}

fun <T> T.onStartCollectingState(onStart: () -> Unit) where T : ViewModel, T : StateHolder<*> {
    setupFlow(scope = viewModelScope, onStart = onStart)
}
