package com.iagoaf.appfinancas.src.features.auth.external.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.iagoaf.appfinancas.core.result.BaseResult
import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel
import com.iagoaf.appfinancas.src.features.auth.infra.service.IAuthService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
) : IAuthService {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    override suspend fun register(
        name: String,
        email: String,
        password: String,
    ): BaseResult<UserModel> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = UserModel(
                uid = authResult.user?.uid ?: "",
                email = authResult.user?.email ?: "",
                name = name
            )
            return BaseResult.Success(user)
        } catch (e: Exception) {
            BaseResult.Error(
                message = e.message ?: "Unknown error"
            )
        }
    }

    override suspend fun createUser(user: UserModel, name: String): BaseResult<Unit> {
        val aux = mapOf(
            "uid" to user.uid,
            "email" to user.email,
            "name" to name
        )

        firestore.collection("users").document(user.uid).set(aux).await()

        return BaseResult.Success(Unit)
    }

    override suspend fun getUserInfo(userId: String): BaseResult<UserModel?> {
        return try {
            val result = firestore.collection("users").document(userId).get().await()
            val user = result.toObject(UserModel::class.java)
            return BaseResult.Success(user)
        } catch (e: Exception) {
            BaseResult.Error(
                message = e.message ?: "Unknown error"
            )
        }
    }


    override suspend fun signIn(
        email: String,
        password: String
    ): BaseResult<UserModel> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = UserModel(
                uid = result.user?.uid ?: "",
                email = result.user?.email ?: "",
                name = ""
            )
            return BaseResult.Success(user)
        } catch (e: Exception) {
            BaseResult.Error(
                message = e.message ?: "Unknown error"
            )
        }
    }

    override suspend fun signOut(): BaseResult<Unit> {
        return try {
            auth.signOut()
            return BaseResult.Success(Unit)
        } catch (e: Exception) {
            BaseResult.Error(
                message = e.message ?: "Unknown error"
            )
        }
    }


    override suspend fun getCurrentUser(): BaseResult<UserModel> {
        return try {
            val firebaseUser = auth.currentUser
            val user = UserModel(
                uid = firebaseUser?.uid ?: "",
                email = firebaseUser?.email ?: "",
                name = ""
            )
            return BaseResult.Success(user)
        } catch (e: Exception) {
            BaseResult.Error(
                message = e.message ?: "Unknown error"
            )
        }
    }
}