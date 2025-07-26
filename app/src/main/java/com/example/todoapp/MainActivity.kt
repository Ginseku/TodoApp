package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import com.example.todoapp.navigation.AuthViewModel
import com.example.todoapp.navigation.NavController
import com.example.ui.theme.AppTheme


class MainActivity : ComponentActivity() {

    private val authViewModel by lazy { AuthViewModel(applicationContext) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(color = Color.White) {
                    NavController(authViewModel)
                }
            }
        }
    }
}


