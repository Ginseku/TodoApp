package com.example.todoapp.screens.Autentification.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterScreenViewModel() : ViewModel() {
    var regState by mutableStateOf(RegisterScreenState())
    private set
    fun onEvent(event: RegisterScreenEvent){

        when(event){
            is RegisterScreenEvent.OnEmailChange -> regState = regState.copy( email = event.email)
            is RegisterScreenEvent.OnUserNameChange -> regState = regState.copy(userName = event.userName)
            is RegisterScreenEvent.OnPasswordChange ->regState = regState.copy(password = event.password)
        }

    }
}