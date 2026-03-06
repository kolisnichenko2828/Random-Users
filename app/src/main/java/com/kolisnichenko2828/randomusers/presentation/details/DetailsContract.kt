package com.kolisnichenko2828.randomusers.presentation.details

interface DetailsContract {
    data class State(
        val isLoading: Boolean = false,
        val userDetails: DetailsUiModel? = null,
        val error: Throwable? = null
    )
}