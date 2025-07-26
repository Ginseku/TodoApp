package com.example.todoapp.screens.Autentification.login

sealed class LoginScreenEvent{
    data class OnUserNameChange(val userName: String) : LoginScreenEvent()
    data class OnPasswordChange(val password: String) : LoginScreenEvent()
}

data class LoginScreenState(
    val userName: String = "",
    val password: String = ""
)