package com.iagoaf.appfinancas.src.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagoaf.appfinancas.src.features.auth.domain.repository.IAuthRepository
import com.iagoaf.appfinancas.src.features.auth.presentation.listener.LoginListener
import com.iagoaf.appfinancas.src.features.auth.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel()
class LoginViewModel @Inject constructor(
    val authRepository: IAuthRepository
) : ViewModel() {


    private val _state = MutableStateFlow<LoginState>(LoginState.Initial)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _listener = MutableStateFlow<LoginListener>(LoginListener.Idle)
    val listener: StateFlow<LoginListener> = _listener.asStateFlow()

    fun login(email: String, password: String, onEnd: () -> Unit) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = authRepository.signIn(email, password)
            result.onSuccess {
                _listener.value = LoginListener.OnLoginSuccess
            }
            result.onError {
                _listener.value = LoginListener.OnLoginError(it)
            }
            _state.value = LoginState.Initial
            onEnd()
        }
    }


}