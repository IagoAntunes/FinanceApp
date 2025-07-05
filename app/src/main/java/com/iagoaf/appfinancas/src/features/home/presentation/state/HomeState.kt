package com.iagoaf.appfinancas.src.features.home.presentation.state

import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel

sealed class HomeState(open val user: UserModel?) {

    data class Idle(override val user: UserModel?) : HomeState(user)
    data class Loading(override val user: UserModel?) : HomeState(user)
    data class Success(
        override val user: UserModel?
    ) : HomeState(user)

    data class Error(
        val message: String,
        override val user: UserModel?
    ) : HomeState(user)
}
