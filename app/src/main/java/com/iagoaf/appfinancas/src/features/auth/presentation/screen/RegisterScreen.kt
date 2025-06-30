package com.iagoaf.appfinancas.src.features.auth.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iagoaf.appfinancas.R
import com.iagoaf.appfinancas.core.components.CTextField
import com.iagoaf.appfinancas.core.ui.theme.appTypography
import com.iagoaf.appfinancas.core.ui.theme.gray100
import com.iagoaf.appfinancas.core.ui.theme.gray400
import com.iagoaf.appfinancas.core.ui.theme.gray700
import com.iagoaf.appfinancas.core.ui.theme.magenta
import com.iagoaf.appfinancas.src.features.auth.presentation.listener.RegisterListener
import com.iagoaf.appfinancas.src.features.auth.presentation.state.RegisterState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    state: RegisterState,
    listener: RegisterListener,
    onRegister: (String, String, String) -> Unit,
    onBackToLogin: () -> Unit,
) {

    val nameValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val isFieldsValid = remember { mutableStateOf(false) }
    val obscurePassword = remember { mutableStateOf(true) }

    fun verifyValidFields() {
        isFieldsValid.value = nameValue.value.isNotEmpty() &&
                emailValue.value.isNotEmpty() &&
                passwordValue.value.isNotEmpty()
    }

    fun clearFields() {
        nameValue.value = ""
        emailValue.value = ""
        passwordValue.value = ""
    }


    LaunchedEffect(listener) {
        when (listener) {
            is RegisterListener.OnRegisterSuccess -> {
                clearFields()
                onBackToLogin()
            }

            is RegisterListener.OnRegisterError -> {
                // Handle registration error, e.g., show an error message
            }

            else -> {}
        }
    }

    Scaffold(
        containerColor = gray100,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = gray100,
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackToLogin() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = {
                    Text(
                        "Register",
                        style = appTypography.titleMd,
                        color = gray700
                    )
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 12.dp, end = 12.dp, top = 24.dp, bottom = 12.dp)
        ) {
            CTextField(
                value = nameValue.value,
                onValueChange = {
                    nameValue.value = it
                    verifyValidFields()
                },
                enabled = state !is RegisterState.Loading,
                placeHolder = {
                    Text(
                        "Name",
                        style = appTypography.input, color = gray400,
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            CTextField(
                value = emailValue.value,
                onValueChange = {
                    emailValue.value = it
                    verifyValidFields()
                },
                enabled = state !is RegisterState.Loading,
                placeHolder = {
                    Text(
                        "E-mail",
                        style = appTypography.input, color = gray400,
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            CTextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordValue.value,
                onValueChange = { text ->
                    passwordValue.value = text
                },
                enabled = state !is RegisterState.Loading,
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
            Spacer(Modifier.height(12.dp))
            Button(
                enabled = isFieldsValid.value,
                onClick = {
                    onRegister(
                        nameValue.value,
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (state != RegisterState.Loading) {
                        Text(
                            "Register",
                            style = appTypography.buttonMd,
                            color = gray100,
                        )
                    } else {
                        CircularProgressIndicator(
                            color = gray100,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(
        state = RegisterState.Idle,
        listener = RegisterListener.Idle,
        onRegister = { _, _, _ -> },
        onBackToLogin = {}
    )
}