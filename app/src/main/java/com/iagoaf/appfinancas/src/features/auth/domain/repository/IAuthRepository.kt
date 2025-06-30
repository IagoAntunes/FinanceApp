package com.iagoaf.appfinancas.src.features.auth.domain.repository

import com.iagoaf.appfinancas.core.result.BaseResult
import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel

interface IAuthRepository {
    suspend fun register(name: String, email: String, password: String): BaseResult<UserModel>
    suspend fun signIn(email: String, password: String): BaseResult<UserModel>
}