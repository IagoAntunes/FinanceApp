package com.iagoaf.appfinancas.src.features.auth.infra.service

import com.iagoaf.appfinancas.core.result.BaseResult
import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel

interface IAuthService {

    suspend fun register(
        name: String,
        email: String,
        password: String,
    ): BaseResult<UserModel>

    suspend fun createUser(
        user: UserModel,
        name: String
    ): BaseResult<Unit>

    suspend fun signIn(
        email: String,
        password: String,
    ): BaseResult<UserModel>

    suspend fun signOut(): BaseResult<Unit>

    suspend fun getUserInfo(userId: String): BaseResult<UserModel?>
    suspend fun getCurrentUser(): BaseResult<UserModel>

}