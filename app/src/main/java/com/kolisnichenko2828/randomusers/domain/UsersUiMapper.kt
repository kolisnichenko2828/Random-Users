package com.kolisnichenko2828.randomusers.domain

import com.kolisnichenko2828.randomusers.presentation.users.UsersUiModel

fun UsersModel.toItemUiModel(): UsersUiModel {
    return UsersUiModel(
        id = this.id,
        fullName = listOf(this.firstName, this.lastName)
            .filter { it.isNotBlank() }
            .joinToString(" "),
    )
}

fun List<UsersModel>.toUsersUiModels(): List<UsersUiModel> {
    return this.map { it.toItemUiModel() }
}