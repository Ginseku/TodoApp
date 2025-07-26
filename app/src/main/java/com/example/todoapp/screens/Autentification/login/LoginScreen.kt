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
import com.example.todoapp.screens.Autentification.LogButton
import com.example.todoapp.screens.Autentification.LogPasswordTextFields
import com.example.todoapp.screens.Autentification.LogUserNameTextFields
import com.example.todoapp.screens.Autentification.NotRegisterGoRegisterButton


@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    val viewModel = viewModel<LoginScreenViewModel>()
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        LogUserNameTextFields(
            state = viewModel.state,
            onEvent = viewModel::onEvent
        )
        Spacer(modifier = Modifier.height(8.dp))
        LogPasswordTextFields(
            state = viewModel.state,
            onEvent = viewModel::onEvent
        )
        Spacer(modifier = Modifier.height(16.dp))
        LogButton(onLoginSuccess = { onLoginSuccess() },Alignment.BottomCenter)
        Spacer(modifier = Modifier.height(5.dp))
        NotRegisterGoRegisterButton(onNavigateToRegister = { onNavigateToRegister() })
    }
}

