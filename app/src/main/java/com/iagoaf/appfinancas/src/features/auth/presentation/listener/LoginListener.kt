package com.iagoaf.appfinancas.src.features.auth.presentation.listener

sealed class LoginListener{
    object Idle : LoginListener()
    object OnLoginSuccess : LoginListener()
    data class OnLoginError(val error: String) : LoginListener()
}