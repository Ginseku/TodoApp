package com.example.todoapp.screens.Autentification.register

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
import com.example.todoapp.screens.Autentification.HaveAccountGoLoginButton
import com.example.todoapp.screens.Autentification.RegButton
import com.example.todoapp.screens.Autentification.RegEmailTextFields
import com.example.todoapp.screens.Autentification.RegPasswordTextFields
import com.example.todoapp.screens.Autentification.RegUserNameTextFields


@Composable
fun RegistrationScreen(onRegisterSuccess: () -> Unit, onBack: () -> Unit){
    val viewModel = viewModel<RegisterScreenViewModel>()
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RegEmailTextFields(
            state = viewModel.regState,
            onEvent = viewModel::onEvent,

        )
        Spacer(modifier = Modifier.height(8.dp))
        RegUserNameTextFields(
            state =  viewModel.regState,
            onEvent = viewModel::onEvent
        )
        Spacer(modifier = Modifier.height(8.dp))
        RegPasswordTextFields(
            state =  viewModel.regState,
            onEvent = viewModel::onEvent
        )
        Spacer(modifier = Modifier.height(16.dp))
        RegButton(onRegisterSuccess = {Unit},Alignment.BottomCenter)
        Spacer(modifier = Modifier.height(5.dp))
        HaveAccountGoLoginButton ( onBack = {onBack()})
    }
}







