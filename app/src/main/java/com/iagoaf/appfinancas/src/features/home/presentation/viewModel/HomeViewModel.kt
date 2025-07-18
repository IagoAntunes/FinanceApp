package com.iagoaf.appfinancas.src.features.home.presentation.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagoaf.appfinancas.core.utils.Month
import com.iagoaf.appfinancas.services.database.keyValue.PreferencesManager
import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel
import com.iagoaf.appfinancas.src.features.auth.domain.repository.IAuthRepository
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel
import com.iagoaf.appfinancas.src.features.home.domain.model.ReleaseModel
import com.iagoaf.appfinancas.src.features.home.domain.repository.IBudgetRepository
import com.iagoaf.appfinancas.src.features.home.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val preferencesManager: PreferencesManager,
    val authRepository: IAuthRepository,
    val budgetRepository: IBudgetRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Idle(null))
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private var _user: UserModel? = null

    init {
        getUserData()
        getBudgets()
    }

    fun getBudgets() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = budgetRepository.getBudgets()
            val user = _user
            val actualMonth = LocalDate.now().month
            val selectedMonth = Month.list.first { month ->
                month.ordinal == actualMonth.ordinal
            }
            result.onSuccess { items ->
                val currentBudget = items.items.firstOrNull { budget ->
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
                    currentBudget = currentBudget ?: BudgetModel(),
                )
            }
            result.onError { error ->
                Log.e("ERRO AQUI", error ?: "Unknown error")
            }
        }
    }


    fun getUserData() {
        val userEmail = preferencesManager.get("user_email")
        val userName = preferencesManager.get("user_name") ?: ""
        val userId = preferencesManager.get("user_uid") ?: ""
        _user = UserModel(
            email = userEmail ?: "",
            uid = userId,
            name = userName,
        )
        if (userEmail != null) {
            _state.value = HomeState.Idle(
                user = _user
            )
        }
    }

    fun changeMonth(month: Month) {
        _state.value = _state.value
            .copy(month = month)
        if (_state.value is HomeState.Success) {
            val selectedBudget = (_state.value as HomeState.Success).budgets.firstOrNull { budget ->
                val budgetMonth = LocalDate.parse(
                    budget.date,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                ).month
                budgetMonth.ordinal == (_state.value as HomeState.Success).selectedMonth.ordinal
            }
            (_state.value as HomeState.Success).currentBudget = selectedBudget ?: BudgetModel()
        }
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

    fun addRelease(release: ReleaseModel) {
        val id = UUID.randomUUID().toString()
        release.id = id
    }


}