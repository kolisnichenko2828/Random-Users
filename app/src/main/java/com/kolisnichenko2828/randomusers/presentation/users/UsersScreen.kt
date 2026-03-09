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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.randomusers.R
import com.kolisnichenko2828.randomusers.core.toUserReadableMessage
import com.kolisnichenko2828.randomusers.presentation.users.components.ErrorMessage
import com.kolisnichenko2828.randomusers.presentation.users.components.UsersContent

@Composable
fun UsersScreen(
    onUserClick: (String) -> Unit,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    LaunchedEffect(Unit) {
        if (currentState.users.isEmpty()) {
            viewModel.setEvent(UsersContract.Event.InitialLoad())
        }
    }

    PullToRefreshBox(
        isRefreshing = currentState.isRefreshing,
        onRefresh = { viewModel.setEvent(UsersContract.Event.Refresh()) },
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            currentState.isLoadingInitial -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            currentState.error != null && uiState.users.isEmpty() -> {
                ErrorMessage(
                    errorMessage = currentState.error.toUserReadableMessage(),
                    onRetry = { viewModel.setEvent(UsersContract.Event.InitialLoad()) }
                )
            }
            currentState.users.isNotEmpty() -> {
                UsersContent(
                    users = currentState.users,
                    isLoadingNext = currentState.isLoadingNext,
                    isError = currentState.error?.toUserReadableMessage(),
                    onItemVisible = { viewModel.setEvent(UsersContract.Event.OnItemVisible(it)) },
                    onLoadNext = { viewModel.setEvent(UsersContract.Event.LoadNext()) },
                    onUserClick = onUserClick,
                )
            }
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.nothing_found),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}