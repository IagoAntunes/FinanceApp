package com.iagoaf.appfinancas.src.features.home.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagoaf.appfinancas.core.utils.Month
import com.iagoaf.appfinancas.services.database.keyValue.PreferencesManager
import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel
import com.iagoaf.appfinancas.src.features.auth.domain.repository.IAuthRepository
import com.iagoaf.appfinancas.src.features.home.domain.repository.IBudgetRepository
import com.iagoaf.appfinancas.src.features.home.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val preferencesManager: PreferencesManager,
    val authRepository: IAuthRepository,
    val budgetRepository: IBudgetRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Idle(null))
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        getUserData()
        getBudgets()
    }

    fun getBudgets() {
        viewModelScope.launch {
            val result = budgetRepository.getBudgets()
            val user = (_state.value as? HomeState.Idle)?.user
            val actualMonth = LocalDate.now().month
            val selectedMonth = Month.list.first { month ->
                month.ordinal == actualMonth.ordinal
            }
            result.onSuccess { items ->
                val currentBudget = items.items.first { budget ->
                    val budgetMonth = LocalDate.parse(
                        budget.date,
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    ).month
                    budgetMonth.ordinal == selectedMonth.ordinal
                }
                _state.value = HomeState.Success(
                    user = user,
                    selectedMonth = selectedMonth,
                    budgets = items.items,
                    currentBudget = currentBudget,
                )
            }
            result.onError {
                //
            }
        }
    }


    fun getUserData() {
        val userEmail = preferencesManager.get("user_email")
        val userName = preferencesManager.get("user_name") ?: ""
        val userId = preferencesManager.get("user_uid") ?: ""
        if (userEmail != null) {
            _state.value = HomeState.Idle(
                user = UserModel(
                    email = userEmail,
                    uid = userId,
                    name = userName,
                ),
            )
        }
    }

    fun changeMonth(month: Month) {
        _state.value = _state.value.copy(month = month)
    }

    fun onLeftChangeMonth() {
        val currentMonth = (_state.value as HomeState.Success).selectedMonth
        val newMonth =
            Month.entries[(currentMonth.ordinal - 1 + Month.entries.size) % Month.entries.size]
        changeMonth(newMonth)
    }

    fun onRightChangeMonth() {
        val currentMonth = (_state.value as HomeState.Success).selectedMonth
        val newMonth = Month.entries[(currentMonth.ordinal + 1) % Month.entries.size]
        changeMonth(newMonth)

    }


}