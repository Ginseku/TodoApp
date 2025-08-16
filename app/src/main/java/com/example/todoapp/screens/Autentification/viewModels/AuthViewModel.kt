package com.example.todoapp.screens.Autentification.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.screens.Autentification.LoginRequest
import com.example.todoapp.screens.Autentification.RegisterRequest
import com.example.todoapp.screens.Autentification.TokenManager
import com.example.todoapp.screens.Autentification.network.RetrofitInstance
import kotlinx.coroutines.launch

class AuthViewModel(private val tokenManager: TokenManager) : ViewModel() {
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var token by mutableStateOf<String?>(tokenManager.getToken())
        private set

    fun login(username: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitInstance.api.login(LoginRequest(username, password))
                if (response.isSuccessful) {
                    token = response.body()?.token
                    token?.let { tokenManager.saveToken(it) }
                    onSuccess()
                } else {
                    errorMessage = "Email or password incorrect: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Ошибка сети: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun register(username: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitInstance.api.register(RegisterRequest(username, email, password))
                if (response.isSuccessful) {
                    token = response.body()?.token
                    token?.let { tokenManager.saveToken(it) }
                    onSuccess()
                } else {
                    errorMessage = "Registration failed: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Ошибка сети: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
