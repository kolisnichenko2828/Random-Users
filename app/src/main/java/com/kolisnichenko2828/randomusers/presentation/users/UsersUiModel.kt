package com.kolisnichenko2828.randomusers.presentation.users

import androidx.compose.runtime.Immutable

@Immutable
data class UsersUiModel(
    val uuid: String,
    val fullName: String,
)