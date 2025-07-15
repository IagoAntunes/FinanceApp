package com.iagoaf.appfinancas.src.features.home.presentation.state

import com.iagoaf.appfinancas.core.utils.Month
import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel

sealed class HomeState(open val user: UserModel?) {

    data class Idle(
        override val user: UserModel?,
    ) :
        HomeState(user)

    data class Success(
        override val user: UserModel?,
        var selectedMonth: Month = Month.JANUARY,
        var currentBudget: BudgetModel,
        val budgets: List<BudgetModel>,
    ) :
        HomeState(user)

    data class Error(
        val message: String,
        override val user: UserModel?,
    ) : HomeState(user)


    fun copy(month: Month?): HomeState {
        return when (this) {
            is Idle -> this.copy()
            is Error -> this.copy()
            is Success -> this.copy(selectedMonth = month ?: this.selectedMonth)
        }
    }

}
