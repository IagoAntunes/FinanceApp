package com.iagoaf.appfinancas.src.features.auth.presentation.state

sealed class RegisterState{
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()
    object Loading : RegisterState()
    object Idle : RegisterState()
}