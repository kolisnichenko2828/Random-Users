package com.kolisnichenko2828.randomusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kolisnichenko2828.randomusers.presentation.navigation.RandomUsersApp
import com.kolisnichenko2828.randomusers.presentation.theme.RandomUsersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomUsersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    RandomUsersApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}