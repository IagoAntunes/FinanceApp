package com.iagoaf.appfinancas.src.features.home.infra.service

import com.iagoaf.appfinancas.core.result.BaseResult
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetResponse

interface IBudgetService {
    suspend fun getBudgets(userId: String): BaseResult<BudgetResponse>
}