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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
) {

    val obscurePassword = remember { mutableStateOf(true) }

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

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
                "BOAS VINDAS!",
                style = appTypography.titleSm,
                color = gray700,
            )
            Text(
                "Pronto para organizar suas finanças? Acesse agora",
                style = appTypography.textSm,
                color = gray500,
            )
            Spacer(modifier = Modifier.height(28.dp))
            CTextField(
                modifier = Modifier.fillMaxWidth(),
                value = emailValue.value,
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
                    "Registrar",
                    style = appTypography.buttonMd,
                    color = magenta,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {},
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
                Text(
                    "Entrar",
                    style = appTypography.buttonMd,
                    color = gray100,
                )
            }
        }
    }


}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onNavigateToRegister = {},
        onLogin = { _, _ -> }
    )
}