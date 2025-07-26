package com.example.todoapp.screens.Autentification.register

sealed class RegisterScreenEvent{
    data class OnUserNameChange(val userName: String) : RegisterScreenEvent()
    data class OnEmailChange(val email: String) : RegisterScreenEvent()
    data class OnPasswordChange(val password: String) : RegisterScreenEvent()
}

data class RegisterScreenState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
)