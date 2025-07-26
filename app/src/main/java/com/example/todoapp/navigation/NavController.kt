package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.todoapp.MainScreen
import com.example.todoapp.screens.Autentification.login.LoginScreen
import com.example.todoapp.screens.Autentification.register.RegistrationScreen

@Composable
fun NavController(authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (authViewModel.isUserLoged) "main" else "auth"
    )
    { //auth
        navigation(startDestination = "login", route = "auth"){
            composable("login"){
                LoginScreen(
                    onLoginSuccess = {
                        authViewModel.login()
                        navController.navigate("main") {
                            popUpTo("auth") {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate("auth/register")

                    }
                )
            }
            composable("register"){
                RegistrationScreen(
                    onRegisterSuccess = {
                        authViewModel.login()
                        navController.navigate("main") {
                            popUpTo("auth") {
                                inclusive = true
                            }
                        }
                    },
                    onBack= {
                        navController.navigate("auth/login")

                    }
                )
            }
            composable("main") {
                MainScreen() // тут будет NavHostContainer + BottomNavigationBar
            }

        }
    }
}