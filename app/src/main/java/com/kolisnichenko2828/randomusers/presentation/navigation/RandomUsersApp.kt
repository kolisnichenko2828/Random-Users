package com.kolisnichenko2828.randomusers.presentation.navigation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.kolisnichenko2828.randomusers.presentation.details.DetailsScreen
import com.kolisnichenko2828.randomusers.presentation.users.UsersScreen
import kotlinx.parcelize.Parcelize

@Composable
fun RandomUsersApp(
    modifier: Modifier = Modifier
) {
    val backStack = rememberSaveable { mutableStateListOf<Screen>(Screen.Users) }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.Users> {
                UsersScreen(
                    onUserClick = { uuid ->
                        backStack.add(Screen.Details(uuid = uuid))
                    }
                )
            }
            entry<Screen.Details> {
                DetailsScreen(
                    uuid = it.uuid,
                )
            }
        }
    )
}

sealed interface Screen : Parcelable {
    @Parcelize
    object Users : Screen
    @Parcelize
    data class Details(val uuid: String) : Screen
}