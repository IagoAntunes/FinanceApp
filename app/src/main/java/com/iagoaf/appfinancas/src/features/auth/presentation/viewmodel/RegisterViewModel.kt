package com.iagoaf.appfinancas.src.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagoaf.appfinancas.src.features.auth.domain.repository.IAuthRepository
import com.iagoaf.appfinancas.src.features.auth.presentation.listener.RegisterListener
import com.iagoaf.appfinancas.src.features.auth.presentation.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val authRepository: IAuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _listener = MutableStateFlow<RegisterListener>(RegisterListener.Idle)
    val listener: StateFlow<RegisterListener> = _listener.asStateFlow()


    fun register(
        name: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _state.value = RegisterState.Loading
            val result = authRepository.register(name, email, password)
            result.onSuccess {
                _state.value = RegisterState.Success("Registration successful")
                _listener.value = RegisterListener.OnRegisterSuccess
            }
            result.onError { error ->
                _state.value = RegisterState.Error(error)
                _listener.value = RegisterListener.OnRegisterError(error)
            }
        }
    }


}