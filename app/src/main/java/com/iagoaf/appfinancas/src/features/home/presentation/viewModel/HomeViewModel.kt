package com.iagoaf.appfinancas.src.features.home.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagoaf.appfinancas.services.database.keyValue.PreferencesManager
import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel
import com.iagoaf.appfinancas.src.features.auth.domain.repository.IAuthRepository
import com.iagoaf.appfinancas.src.features.home.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val preferencesManager: PreferencesManager,
    val authRepository: IAuthRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow<HomeState>(HomeState.Idle(null))
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        getUserData()
    }

    fun getUserData() {
        val userEmail = preferencesManager.get("user_email")
        val userName = preferencesManager.get("user_name") ?: ""
        val userId = preferencesManager.get("user_uid") ?: ""
        if (userEmail != null) {
            _state.value = HomeState.Idle(
                user = UserModel(
                    email = userEmail,
                    uid = userId,
                    name = userName,
                )
            )
        }
    }


}