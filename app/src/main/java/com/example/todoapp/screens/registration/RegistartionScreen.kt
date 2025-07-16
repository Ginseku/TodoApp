package com.example.todoapp.screens.registration

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RegistrationScreen() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EmailTextFields(
            email = email,
            onEmailChange = {email = it},

        )
        Spacer(modifier = Modifier.height(8.dp))
        PasswordTextFields(
            password = password,
            onPasswordChange = {password = it}
        )
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton(Alignment.BottomCenter)
        Spacer(modifier = Modifier.height(5.dp))
        NotRegisterGoRegisterButton()
    }
}


@Composable
fun EmailTextFields(
    email:String,
    onEmailChange:(String)->Unit
) {
    var state by remember { mutableStateOf("") }


    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text(text = "Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)

    )

}


@Composable
fun PasswordTextFields(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(text = "Password") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    )

}

@Composable
fun LoginButton(bottomCenter: Alignment) {
    Button(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .height(50.dp),
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(5.dp),
    ) {
        Text(text = "Login")
    }
}

@Composable
fun NotRegisterGoRegisterButton(){
    TextButton(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .height(45.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = { /*TODO*/ },
    ){
        Text(text = "Not register? Click here and register!",
            fontSize = 10.sp,
            color = androidx.compose.ui.graphics.Color.White)
    }
}
