package com.example.todoapp.screens.Autentification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.screens.Autentification.login.LoginScreenEvent
import com.example.todoapp.screens.Autentification.login.LoginScreenState
import com.example.todoapp.screens.Autentification.register.RegisterScreenEvent
import com.example.todoapp.screens.Autentification.register.RegisterScreenState
import com.example.todoapp.screens.Autentification.viewModels.AuthViewModel
import android.net.Uri

@Composable
fun LoginFields(viewModel: AuthViewModel, navController: NavController, onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            value = username, onValueChange = { username = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                viewModel.login(username, password) {
                    val savedToken = TokenManager(context).getToken() ?: ""
                    val encodedToken = Uri.encode(savedToken) // кодируем JWT

                    navController.navigate("main") {
                        popUpTo("auth") { inclusive = true }
                    }
                    onLoginSuccess()
                }
            },
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Login")
        }

        if (viewModel.errorMessage != null) {
            Text(text = viewModel.errorMessage!!, color = Color.Red)
        }
    }
}

@Composable
fun RegistrationField(viewModel: AuthViewModel, onRegisterSuccess: () -> Unit) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Password") },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                viewModel.register(username, email, password) {
                    onRegisterSuccess()
                }
            },
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Registration")
        }
    }
}

@Composable
fun NotRegisterGoRegisterButton(onNavigateToRegister: () -> Unit) {
    TextButton(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .height(45.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = { onNavigateToRegister() },
    ) {
        Text(
            text = stringResource(id = R.string.not_register_click_here_and_register),
            fontSize = 10.sp,
            color = androidx.compose.ui.graphics.Color.White,
            modifier = Modifier
                .padding(top = 15.dp)
        )
    }
}


@Composable
fun HaveAccountGoLoginButton(onBack: () -> Unit) {
    TextButton(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .height(45.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = { onBack() },
    ) {
        Text(
            text = stringResource(id = R.string.alredy_have_account_click_here_and_login),
            fontSize = 10.sp,
            color = androidx.compose.ui.graphics.Color.White
        )
    }
}