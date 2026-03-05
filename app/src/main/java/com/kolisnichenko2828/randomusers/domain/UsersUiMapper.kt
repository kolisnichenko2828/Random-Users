package com.kolisnichenko2828.randomusers.domain

import com.kolisnichenko2828.randomusers.presentation.users.UsersUiModel

fun UsersModel.toItemUiModel(): UsersUiModel {
    return UsersUiModel(
        uuid = this.uuid,
        fullName = listOf(this.firstName, this.lastName)
            .filter { it.isNotBlank() }
            .joinToString(" "),
    )
}