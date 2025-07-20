package com.iagoaf.appfinancas.src.features.monthlyBudget.presentation.listener

sealed class MonthlyBudgetListener {
    object Idle :
        MonthlyBudgetListener()

    object BudgetCreated :
        MonthlyBudgetListener()

    object BudgetDeleted :
        MonthlyBudgetListener()
}