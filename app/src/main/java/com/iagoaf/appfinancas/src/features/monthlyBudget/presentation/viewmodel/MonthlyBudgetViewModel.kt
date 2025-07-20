package com.iagoaf.appfinancas.src.features.monthlyBudget.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagoaf.appfinancas.core.utils.Month
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel
import com.iagoaf.appfinancas.src.features.home.domain.repository.IBudgetRepository
import com.iagoaf.appfinancas.src.features.monthlyBudget.presentation.listener.MonthlyBudgetListener
import com.iagoaf.appfinancas.src.features.monthlyBudget.presentation.state.MonthlyBudgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MonthlyBudgetViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val budgetRepository: IBudgetRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MonthlyBudgetState>(MonthlyBudgetState.Loading)
    val state = _state.asStateFlow()

    private val _listener = MutableStateFlow<MonthlyBudgetListener>(MonthlyBudgetListener.Idle)
    val listener = _listener.asStateFlow()

    var formattedDate: String
    var date: LocalDate
    var year: Int
    var monthNumber: Int = 0

    init {
        val monthParam = savedStateHandle.get<String>("month")
        val currentDate = LocalDate.now()
        year = currentDate.year
        val parsedMonth = monthParam?.toIntOrNull() ?: currentDate.monthValue
        date = LocalDate.of(year, parsedMonth, 1)
        formattedDate = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
        monthNumber = formattedDate.split("/").getOrNull(0)?.toIntOrNull()?.minus(1)!!
        getBudgets()
    }


    fun deleteBudget(budget: BudgetModel) {
        val currentState = state.value as? MonthlyBudgetState.Success ?: return
        val budgets = currentState.budgets.toMutableList()
        val index = budgets.indexOfFirst { it.date == budget.date }

        if (index != -1) {
            budgets.removeAt(index)

            viewModelScope.launch {
                val result = budgetRepository.deleteBudget(budgets)
                result.onSuccess {
                    _listener.value = MonthlyBudgetListener.BudgetDeleted
                    getBudgets()
                }
                result.onError {
                }
            }
        }
    }


    fun createBudget(limit: String) {
        viewModelScope.launch {
            val date = LocalDate.of(year, monthNumber + 1, 1).format(
                DateTimeFormatter.ofPattern("MM/dd/yyyy")
            )
            val budget = BudgetModel(
                limit = limit,
                date = date.toString(),
            )
            val result = budgetRepository.createBudget(budget)
            result.onSuccess {
                _listener.value = MonthlyBudgetListener.BudgetCreated
            }
            result.onError {
            }
        }
    }

    private fun getBudgets() {
        viewModelScope.launch {
            val result = budgetRepository.getBudgets()
            result.onSuccess { budgetResponse ->

                val monthName = monthNumber.let { num ->
                    Month.list.firstOrNull { it.od == num }?.name
                } ?: "UNKNOWN"

                val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                val sortedBudgets = budgetResponse.items.sortedBy { budget ->
                    try {
                        LocalDate.parse(budget.date, formatter)
                    } catch (e: Exception) {
                        LocalDate.MIN
                    }
                }

                _state.value = MonthlyBudgetState.Success(
                    date = date,
                    formattedDate = (state.value as? MonthlyBudgetState.Success)?.formattedDate
                        ?: "",
                    budgets = sortedBudgets,
                    monthName = monthName,
                    year = year.toString()
                )
            }
            result.onError {

            }
        }
    }
}
