package com.iagoaf.appfinancas.src.features.home.external.service

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.iagoaf.appfinancas.core.result.BaseResult
import com.iagoaf.appfinancas.core.utils.FirebaseKeys
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetResponse
import com.iagoaf.appfinancas.src.features.home.infra.service.IBudgetService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BudgetServiceImpl @Inject constructor() : IBudgetService {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun getBudgets(userId: String): BaseResult<BudgetResponse> {
        return try {
            val documentSnapshot =
                firestore.collection(FirebaseKeys.budgets).document(userId).get().await()
            val budgetResponse = documentSnapshot.toObject(BudgetResponse::class.java)

            if (budgetResponse != null) {
                BaseResult.Success(budgetResponse)
            } else {
                BaseResult.Error("Failed to parse document")
            }
        } catch (e: Exception) {
            BaseResult.Error("Failed to parse document")
        }
    }

    override suspend fun createBudget(budget: BudgetModel, userId: String): BaseResult<Unit> {
        try {
            val docRef =
                firestore.collection(FirebaseKeys.budgets).document(userId)

            docRef.update(FirebaseKeys.items, FieldValue.arrayUnion(budget)).await()

            return BaseResult.Success(Unit)
        } catch (e: Exception) {
            return BaseResult.Error("Failed to create budget: ${e.message}")
        }
    }

    override suspend fun createRelease(
        budgets: List<BudgetModel>,
        userId: String
    ): BaseResult<Unit> {
        try {
            val docRef = firestore.collection(FirebaseKeys.budgets).document(userId)
            docRef.update(FirebaseKeys.items, budgets).await()
            return BaseResult.Success(Unit)
        } catch (e: Exception) {
            return BaseResult.Error("Failed to create release: ${e.message}")
        }
    }

    override suspend fun deleteRelease(
        budgets: List<BudgetModel>,
        userId: String
    ): BaseResult<Unit> {
        try {
            val docRef = firestore.collection(FirebaseKeys.budgets).document(userId)
            docRef.update(FirebaseKeys.items, budgets).await()
            return BaseResult.Success(Unit)
        } catch (e: Exception) {
            return BaseResult.Error("Failed to create release: ${e.message}")
        }
    }

    override suspend fun deleteBudget(
        budgets: List<BudgetModel>,
        userId: String
    ): BaseResult<Unit> {
        try {
            val docRef = firestore.collection(FirebaseKeys.budgets).document(userId)
            docRef.update(FirebaseKeys.items, budgets).await()
            return BaseResult.Success(Unit)
        } catch (e: Exception) {
            return BaseResult.Error("Failed to create release: ${e.message}")
        }
    }
}