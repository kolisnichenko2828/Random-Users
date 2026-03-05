package com.kolisnichenko2828.randomusers.presentation.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kolisnichenko2828.randomusers.R
import com.kolisnichenko2828.randomusers.core.toUserReadableMessage
import com.kolisnichenko2828.randomusers.presentation.users.components.ErrorMessage
import com.kolisnichenko2828.randomusers.presentation.users.components.UsersContent

@Composable
fun UsersScreen(
    onUserClick: (String) -> Unit,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val users = viewModel.usersFlow.collectAsLazyPagingItems()
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    val pagingState = users.loadState.refresh

    LaunchedEffect(pagingState) {
        if (pagingState !is LoadState.Loading) {
            isRefreshing = false
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            users.refresh()
        },
        modifier = Modifier.fillMaxSize()
    ) {
        when (pagingState) {
            is LoadState.Loading if !isRefreshing -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is LoadState.Error -> {
                if (users.itemCount == 0) {
                    ErrorMessage(
                        errorMessage = pagingState.error.toUserReadableMessage(),
                        onRetry = { users.retry() }
                    )
                } else {
                    UsersContent(
                        users = users,
                        onUserClick = { onUserClick(it) }
                    )
                }
            }

            else -> {
                if (users.itemCount == 0 && pagingState is LoadState.NotLoading) {
                    Text(
                        text = stringResource(R.string.nothing_found),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    )
                } else {
                    UsersContent(
                        users = users,
                        onUserClick = { onUserClick(it) }
                    )
                }
            }
        }
    }
}