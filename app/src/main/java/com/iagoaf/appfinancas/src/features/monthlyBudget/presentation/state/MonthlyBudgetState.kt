package com.iagoaf.appfinancas.src.features.monthlyBudget.presentation.state

import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel
import java.time.LocalDate

sealed class MonthlyBudgetState {
    object Idle :
        MonthlyBudgetState()

    object Loading :
        MonthlyBudgetState()

    data class Success(
        val date: LocalDate,
        val formattedDate: String,
        val budgets: List<BudgetModel>,
        val monthName: String,
        val year: String
    ) :
        MonthlyBudgetState()
}