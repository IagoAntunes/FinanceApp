package com.iagoaf.appfinancas.src.features.home.external.service

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.iagoaf.appfinancas.core.result.BaseResult
import com.iagoaf.appfinancas.core.utils.FirebaseCollectionsKeys
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetResponse
import com.iagoaf.appfinancas.src.features.home.infra.service.IBudgetService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BudgetServiceImpl @Inject constructor() : IBudgetService {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun getBudgets(userId: String): BaseResult<BudgetResponse> {
        return try {
            val documentSnapshot =
                firestore.collection(FirebaseCollectionsKeys.budgets).document(userId).get().await()

            val budgetResponse = documentSnapshot.toObject(BudgetResponse::class.java)

            if (budgetResponse != null) {
                BaseResult.Success(budgetResponse)
            } else {
                BaseResult.Error("Failed to parse document")
            }
        } catch (e: Exception) {
            Log.e("TESTE", e.message.toString())
            BaseResult.Error("Failed to parse document")
        }
    }
}