package com.example.todoapp.screens.Autentification

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.screens.Autentification.login.LoginScreenEvent
import com.example.todoapp.screens.Autentification.login.LoginScreenState
import com.example.todoapp.screens.Autentification.register.RegisterScreenEvent
import com.example.todoapp.screens.Autentification.register.RegisterScreenState

@Composable
fun RegEmailTextFields(
    state: RegisterScreenState,
    onEvent: (RegisterScreenEvent) -> Unit
) {
    TextField(
        value = state.email,
        onValueChange = {onEvent(RegisterScreenEvent.OnEmailChange(it))},
        label = { Text(text = stringResource(id = R.string.email)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)

    )

}

@Composable
fun RegPasswordTextFields(
    state: RegisterScreenState,
    onEvent: (RegisterScreenEvent) -> Unit
) {
    TextField(
        value = state.password,
        onValueChange = {onEvent(RegisterScreenEvent.OnPasswordChange(it))},
        label = { Text(text = stringResource(id = R.string.password)) },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    )
}

@Composable
fun RegUserNameTextFields(
    state: RegisterScreenState,
    onEvent: (RegisterScreenEvent) -> Unit
){
    TextField(
        value = state.userName,
        onValueChange = {onEvent(RegisterScreenEvent.OnUserNameChange(it))},
        label = { Text(text = stringResource(id = R.string.username)) },
        modifier = Modifier.fillMaxWidth().padding(6.dp)
    )
}

@Composable
fun LogUserNameTextFields(
    state: LoginScreenState,
    onEvent: (LoginScreenEvent) -> Unit
){
    TextField(
        value = state.userName,
        onValueChange = {onEvent(LoginScreenEvent.OnUserNameChange(it))},
        label = { Text(text = stringResource(id = R.string.username)) },
        modifier = Modifier.fillMaxWidth().padding(6.dp)
    )
}

@Composable
fun LogPasswordTextFields(
    state: LoginScreenState,
    onEvent: (LoginScreenEvent) -> Unit
) {
    TextField(
        value = state.password,
        onValueChange = {onEvent(LoginScreenEvent.OnPasswordChange(it))},
        label = { Text(text = stringResource(id = R.string.password)) },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    )


}

@Composable
fun RegButton(onRegisterSuccess: () -> Unit, bottomCenter: Alignment) {
    Button(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .height(50.dp),
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(5.dp),
    ) {
        Text(text = stringResource(id = R.string.registration))
    }
}
@Composable
fun LogButton(onLoginSuccess: () -> Unit,bottomCenter: Alignment) {
    Button(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .height(50.dp),
        onClick = {onLoginSuccess()},
        shape = RoundedCornerShape(5.dp),
    ) {
        Text(text = stringResource(id = R.string.login))
    }
}

@Composable
fun NotRegisterGoRegisterButton(onNavigateToRegister: () -> Unit){
    TextButton(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .height(45.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = { onNavigateToRegister() },
    ){
        Text(text = stringResource(id = R.string.not_register_click_here_and_register),
            fontSize = 10.sp,
            color = androidx.compose.ui.graphics.Color.White,
            modifier = Modifier
                .padding(top = 15.dp,)
                )
    }
}

@Composable
fun HaveAccountGoLoginButton(onBack: () -> Unit){
    TextButton(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .height(45.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = { onBack() },
    ){
        Text(text = stringResource(id = R.string.alredy_have_account_click_here_and_login),
            fontSize = 10.sp,
            color = androidx.compose.ui.graphics.Color.White)
    }
}