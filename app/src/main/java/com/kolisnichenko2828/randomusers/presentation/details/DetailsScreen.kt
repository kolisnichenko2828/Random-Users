package com.kolisnichenko2828.randomusers.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.randomusers.core.toUserReadableMessage
import com.kolisnichenko2828.randomusers.presentation.details.components.DetailsContent

@Composable
fun DetailsScreen(
    uuid: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    LaunchedEffect(Unit) {
        viewModel.loadUserDetails(uuid)
    }

    when {
        currentState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        currentState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentState.error.toUserReadableMessage(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        currentState.userDetails != null -> {
            DetailsContent(uiModel = currentState.userDetails)
        }
    }
}