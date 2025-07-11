package com.iagoaf.appfinancas.src.features.home.domain.model

data class BudgetModel(
    val availableBudget: String = "",
    val budgetUsed: String = "",
    val date: String = "",
    val limit: String = "",
    val releases: List<ReleaseModel> = emptyList()
)