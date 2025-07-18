package com.iagoaf.appfinancas.src.features.home.infra.repository

import com.iagoaf.appfinancas.core.result.BaseResult
import com.iagoaf.appfinancas.services.database.keyValue.PreferencesManager
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetResponse
import com.iagoaf.appfinancas.src.features.home.domain.repository.IBudgetRepository
import com.iagoaf.appfinancas.src.features.home.infra.service.IBudgetService
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    val service: IBudgetService,
    val preferencesManager: PreferencesManager
) : IBudgetRepository {
    override suspend fun getBudgets(): BaseResult<BudgetResponse> {
        val userId = preferencesManager.get("user_uid")
        return service.getBudgets(userId!!)
    }

    override suspend fun createBudget(budget: BudgetModel): BaseResult<Unit> {
        val userId = preferencesManager.get("user_uid")
        return service.createBudget(budget,userId!!)
    }
}