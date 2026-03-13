package com.kolisnichenko2828.randomusers.di

import com.kolisnichenko2828.randomusers.presentation.details.DetailsViewModel
import com.kolisnichenko2828.randomusers.presentation.users.UsersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel<UsersViewModel> {
        UsersViewModel(
            repository = get()
        )
    }

    viewModel<DetailsViewModel> { params ->
        DetailsViewModel(
            repository = get(),
            uuid = params.get()
        )
    }
}