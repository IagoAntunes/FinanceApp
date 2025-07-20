package com.iagoaf.appfinancas.src.features.auth.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iagoaf.appfinancas.R
import com.iagoaf.appfinancas.core.components.CTextField
import com.iagoaf.appfinancas.core.ui.theme.appTypography
import com.iagoaf.appfinancas.core.ui.theme.gray100
import com.iagoaf.appfinancas.core.ui.theme.gray300
import com.iagoaf.appfinancas.core.ui.theme.gray400
import com.iagoaf.appfinancas.core.ui.theme.gray500
import com.iagoaf.appfinancas.core.ui.theme.gray700
import com.iagoaf.appfinancas.core.ui.theme.magenta
import com.iagoaf.appfinancas.src.features.auth.presentation.listener.LoginListener
import com.iagoaf.appfinancas.src.features.auth.presentation.state.LoginState

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    state: LoginState,
    listener: LoginListener,
) {

    val obscurePassword = remember { mutableStateOf(true) }

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }


    LaunchedEffect(listener) {
        when (listener) {
            is LoginListener.Idle -> {}
            is LoginListener.OnLoginError -> {}
            is LoginListener.OnLoginSuccess -> {
                emailValue.value = ""
                passwordValue.value = ""
            }
        }
    }


    Scaffold(
        containerColor = gray100
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 12.dp, end = 12.dp, top = 24.dp, bottom = 12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.head_logo),
                contentDescription = "Head Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                "WELCOME!",
                style = appTypography.titleSm,
                color = gray700,
            )
            Text(
                "Ready to organize your finances? Access now",
                style = appTypography.textSm,
                color = gray500,
            )
            Spacer(modifier = Modifier.height(28.dp))
            CTextField(
                modifier = Modifier.fillMaxWidth(),
                value = emailValue.value,
                enabled = state !is LoginState.Loading,
                onValueChange = { text ->
                    emailValue.value = text
                },
                placeHolder = {
                    Text(
                        "E-mail",
                        style = appTypography.input, color = gray400,
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CTextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordValue.value,
                enabled = state !is LoginState.Loading,
                onValueChange = { text ->
                    passwordValue.value = text
                },
                visualTransformation = if (obscurePassword.value) PasswordVisualTransformation() else VisualTransformation.None,
                placeHolder = {
                    Text(
                        "Password",
                        style = appTypography.input, color = gray400,
                    )
                },
                suffix = {
                    Icon(
                        painter = painterResource(
                            if (obscurePassword.value)
                                R.drawable.ic_eye_closed
                            else
                                R.drawable.ic_eye_opened
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                obscurePassword.value = !obscurePassword.value
                            }
                    )
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            HorizontalDivider(
                color = gray300,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is LoginState.Loading,
                onClick = {
                    onNavigateToRegister()
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = magenta
                ),
                border = BorderStroke(1.dp, magenta),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(
                    horizontal = 14.dp,
                    vertical = 16.dp
                )
            ) {
                Text(
                    "Register",
                    style = appTypography.buttonMd,
                    color = magenta,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                enabled = (state !is LoginState.Loading) && emailValue.value.isNotBlank() && passwordValue.value.isNotBlank(),
                onClick = {
                    onLogin(
                        emailValue.value,
                        passwordValue.value
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = magenta,
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(
                    horizontal = 14.dp,
                    vertical = 16.dp
                )
            ) {
                if (state is LoginState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        "Login",
                        style = appTypography.buttonMd,
                        color = gray100,
                    )
                }
            }
        }
    }


}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onNavigateToRegister = {},
        onLogin = { _, _ -> },
        state = LoginState.Initial,
        listener = LoginListener.Idle
    )
}