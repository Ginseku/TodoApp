package com.example.todoapp.screens.Autentification.login;
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.screens.Autentification.LogPasswordTextFields
import com.example.todoapp.screens.Autentification.LogUserNameTextFields
import com.example.todoapp.screens.Autentification.LoginFields
import com.example.todoapp.screens.Autentification.NotRegisterGoRegisterButton
import com.example.todoapp.screens.Autentification.viewModels.AuthViewModel


@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        LoginFields(
            viewModel = viewModel<AuthViewModel>(),
            onLoginSuccess = { onLoginSuccess() }
        )

        Spacer(modifier = Modifier.height(1.dp))
        NotRegisterGoRegisterButton(onNavigateToRegister = { onNavigateToRegister() })
    }
}

