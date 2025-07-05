package com.iagoaf.appfinancas.src.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagoaf.appfinancas.src.features.auth.domain.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val authRepository: IAuthRepository
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn: StateFlow<Boolean?> = _isUserLoggedIn.asStateFlow()

    init {
        checkUserLoggedIn()
    }

    fun checkUserLoggedIn() {
        viewModelScope.launch {
            val result = authRepository.isUserLogged()
            result.onSuccess { user ->
                _isUserLoggedIn.value = user.uid.isNotEmpty() && user.email.isNotEmpty()
            }.onError {
                _isUserLoggedIn.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.signOut()
            _isUserLoggedIn.value = false
        }
    }

}