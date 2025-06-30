package com.iagoaf.appfinancas.src.features.auth.infra.repository

import com.iagoaf.appfinancas.core.result.BaseResult
import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel
import com.iagoaf.appfinancas.src.features.auth.domain.repository.IAuthRepository
import com.iagoaf.appfinancas.src.features.auth.infra.service.IAuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val auth: IAuthService
) : IAuthRepository {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): BaseResult<UserModel> {
        val result = auth.register(
            name,
            email,
            password,
        )
        if (result is BaseResult.Success) {
            auth.createUser(
                user = result.data,
                name = name,
            ).let { createUserResult ->
                if (createUserResult is BaseResult.Error) {
                    return BaseResult.Error(createUserResult.message)
                }
            }
        } else if (result is BaseResult.Error) {
            return BaseResult.Error(result.message)
        }

        return result
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): BaseResult<UserModel> {
        val result = auth.signIn(
            email,
            password,
        )
        return result
    }
}