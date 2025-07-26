package com.example.todoapp.navigation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthViewModel(applicationContext: Context) : ViewModel() {
    var isUserLoged by mutableStateOf(false)
        private set

    fun login(){
        isUserLoged = true
    }

    fun logout(){
        isUserLoged = false
    }
}