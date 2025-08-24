package com.example.todoapp

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.API.TaskApi
import com.example.todoapp.navigation.BottomNavigationBar
import com.example.todoapp.navigation.NavHostConteiner

@Composable
fun MainScreen() {
    val navController = rememberNavController()


    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { padding ->
        NavHostConteiner(navController = navController, padding = padding)
    }
}