package com.kolisnichenko2828.randomusers.presentation.navigation

import android.os.Parcelable
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.kolisnichenko2828.randomusers.R
import com.kolisnichenko2828.randomusers.presentation.details.DetailsScreen
import com.kolisnichenko2828.randomusers.presentation.users.UsersScreen
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RandomUsersApp() {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    var currentUuid by rememberSaveable { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavigableListDetailPaneScaffold(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            navigator = navigator,
            listPane = {
                AnimatedPane(
                    modifier = Modifier.zIndex(0f),
                    enterTransition = fadeIn(
                        animationSpec = tween(300)) + scaleIn(tween(300),
                        initialScale = 1.1f
                        ),
                    exitTransition = ExitTransition.None
                ) {
                    UsersScreen(
                        onUserClick = { uuid ->
                            currentUuid = uuid
                            coroutineScope.launch {
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                            }
                        }
                    )
                }
            },
            detailPane = {
                AnimatedPane(
                    modifier = Modifier.zIndex(1f),
                    enterTransition = fadeIn(
                        animationSpec = tween(300)) + scaleIn(tween(300),
                        initialScale = 0.9f
                    ),
                    exitTransition = fadeOut(
                        animationSpec = tween(300)) + scaleOut(tween(300),
                        targetScale = 1.1f
                    )
                ) {
                    when (val displayUuid = currentUuid) {
                        null -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = stringResource(R.string.select_user))
                            }
                        }
                        else -> {
                            DetailsScreen(uuid = displayUuid)
                        }
                    }
                }
            }
        )
    }
}

sealed interface Screen : Parcelable {
    @Parcelize
    object Users : Screen
    @Parcelize
    data class Details(val uuid: String) : Screen
}