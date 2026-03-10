package com.kolisnichenko2828.randomusers.domain.mappers

import com.kolisnichenko2828.randomusers.domain.models.UsersModel
import com.kolisnichenko2828.randomusers.presentation.users.UsersUiModel

fun UsersModel.toUsersUiModel(): UsersUiModel {
    return UsersUiModel(
        uuid = this.uuid,
        fullName = listOf(this.firstName, this.lastName)
            .filter { it.isNotBlank() }
            .joinToString(" "),
    )
}

fun List<UsersModel>.toUiModels(): List<UsersUiModel> {
    return this.map { it.toUsersUiModel() }
}