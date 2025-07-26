package com.example.todoapp.screens.Autentification.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginScreenViewModel(): ViewModel() {
    var state by mutableStateOf(LoginScreenState())
    private set
    fun onEvent(event: LoginScreenEvent){
        when (event){
            is LoginScreenEvent.OnUserNameChange -> state = state.copy(userName = event.userName)
            is LoginScreenEvent.OnPasswordChange -> state = state.copy(password = event.password)

        }
    }


}