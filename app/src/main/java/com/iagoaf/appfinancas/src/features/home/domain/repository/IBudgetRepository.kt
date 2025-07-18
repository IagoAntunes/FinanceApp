package com.iagoaf.appfinancas.src.features.home.domain.repository

import com.iagoaf.appfinancas.core.result.BaseResult
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetResponse

interface IBudgetRepository {
    suspend fun getBudgets(): BaseResult<BudgetResponse>
    suspend fun createBudget(budget: BudgetModel): BaseResult<Unit>
}