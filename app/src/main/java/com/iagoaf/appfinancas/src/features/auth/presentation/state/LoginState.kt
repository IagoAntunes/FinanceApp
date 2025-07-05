package com.iagoaf.appfinancas.src.features.auth.presentation.state

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
}