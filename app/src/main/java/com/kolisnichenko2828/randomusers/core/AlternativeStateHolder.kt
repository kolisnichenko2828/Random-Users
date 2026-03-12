package com.kolisnichenko2828.randomusers.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface AlternativeStateHolder<State> {
    val flow: StateFlow<State>
    val value: State
    fun update(function: (State) -> State)
}

class AlternativeStateHolderImpl<State>(
    private val initialState: State,
    private val onStart: () -> Unit
) {
    operator fun provideDelegate(
        thisRef: ViewModel,
        property: KProperty<*>
    ): ReadOnlyProperty<ViewModel, AlternativeStateHolder<State>> {
        val stateHolder = object : AlternativeStateHolder<State> {
            private val _uiState = MutableStateFlow(initialState)
            override val flow = _uiState
                .onStart {
                    onStart()
                }
                .stateIn(
                    scope = thisRef.viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = initialState
                )
            override val value: State get() = _uiState.value

            override fun update(function: (State) -> State) {
                _uiState.update(function)
            }
        }

        return ReadOnlyProperty { _, _ -> stateHolder }
    }
}

fun <T> uiStateHolder(initialState: T, onStart: () -> Unit = {}) = AlternativeStateHolderImpl(initialState, onStart)