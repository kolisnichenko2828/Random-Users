package com.kolisnichenko2828.randomusers.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.kolisnichenko2828.randomusers.data.remote.UsersRepository
import com.kolisnichenko2828.randomusers.domain.toItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    repository: UsersRepository
) : ViewModel() {
    val usersFlow = repository.getUsers()
        .map { pagingData ->
            pagingData.map { userModel ->
                userModel.toItemUiModel()
            }
        }
        .cachedIn(viewModelScope)
}