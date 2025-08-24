package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.todoapp.navigation.NavViewModel
import com.example.todoapp.navigation.NavController
import com.example.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    private val navViewModel by lazy { NavViewModel(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                Surface(color = Color.White) {
                    NavController(navViewModel)
                }
            }
        }
    }
}
