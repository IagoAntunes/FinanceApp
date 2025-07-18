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
        formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        monthNumber = formattedDate.split("/").getOrNull(1)?.toIntOrNull()?.minus(1)!!
        getBudgets()
    }

    fun createBudget(limit: String) {
        viewModelScope.launch {
            val date = LocalDate.of(year, monthNumber + 1, 1).format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
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
                //
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

                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
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
