package com.kolisnichenko2828.randomusers.di

import com.kolisnichenko2828.randomusers.presentation.details.DetailsViewModel
import com.kolisnichenko2828.randomusers.presentation.users.UsersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::UsersViewModel)
    viewModelOf(::DetailsViewModel)
}