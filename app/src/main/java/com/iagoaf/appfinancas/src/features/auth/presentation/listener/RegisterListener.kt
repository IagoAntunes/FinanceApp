package com.iagoaf.appfinancas.src.features.auth.presentation.listener

sealed class RegisterListener {
    object Idle : RegisterListener()
    object OnRegisterSuccess : RegisterListener()
    data class OnRegisterError(val message: String) : RegisterListener()
}