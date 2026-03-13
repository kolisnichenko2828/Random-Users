package com.kolisnichenko2828.randomusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kolisnichenko2828.randomusers.presentation.navigation.RandomUsersApp
import com.kolisnichenko2828.randomusers.presentation.theme.RandomUsersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomUsersTheme {
                RandomUsersApp()
            }
        }
    }
}