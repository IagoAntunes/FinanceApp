package com.iagoaf.appfinancas.src.features.home.infra.service

import com.iagoaf.appfinancas.core.result.BaseResult
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetResponse

interface IBudgetService {
    suspend fun getBudgets(userId: String): BaseResult<BudgetResponse>
    suspend fun createBudget(budget: BudgetModel, userId: String): BaseResult<Unit>
    suspend fun createRelease(budgets: List<BudgetModel>, userId: String): BaseResult<Unit>
    suspend fun deleteRelease(models: List<BudgetModel>, userId: String): BaseResult<Unit>
    suspend fun deleteBudget(models: List<BudgetModel>, userId: String): BaseResult<Unit>
}