package com.iagoaf.appfinancas.src.features.home.domain.model

data class BudgetResponse(
    val items: List<BudgetModel> = emptyList()
)