package com.iagoaf.appfinancas.src.features.home.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagoaf.appfinancas.core.utils.Month
import com.iagoaf.appfinancas.services.database.keyValue.PreferencesManager
import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel
import com.iagoaf.appfinancas.src.features.auth.domain.repository.IAuthRepository
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel
import com.iagoaf.appfinancas.src.features.home.domain.model.ReleaseModel
import com.iagoaf.appfinancas.src.features.home.domain.repository.IBudgetRepository
import com.iagoaf.appfinancas.src.features.home.presentation.listener.HomeListener
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
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Idle(null))
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _listener = MutableStateFlow<HomeListener>(HomeListener.Idle)
    val listener: StateFlow<HomeListener> = _listener.asStateFlow()

    private var _user: UserModel? = null

    private var _selectedMonth: Month? = null

    init {
        getUserData()
        getBudgets()
    }

    fun resetListener() {
        _listener.value = HomeListener.Idle
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            _listener.value = HomeListener.LoggedOut
        }
    }

    fun addRelease(release: ReleaseModel, currentBudget: BudgetModel) {
        val id = UUID.randomUUID().toString()
        release.id = id

        val budgets = (_state.value as HomeState.Success).budgets.toMutableList()
        val index = budgets.indexOf(currentBudget)

        if (index != -1) {
            val updatedReleases = budgets[index].releases.toMutableList()
            updatedReleases.add(release)

            budgets[index] = budgets[index].copy(releases = updatedReleases)

            viewModelScope.launch {
                val result = budgetRepository.createRelease(budgets)
                result.onSuccess {
                    _listener.value = HomeListener.ReleasedCreated
                }
                result.onError {
                    //
                }
            }
        }
    }

    fun deleteRelease(release: ReleaseModel) {
        val id = UUID.randomUUID().toString()
        release.id = id

        val budgets = (_state.value as HomeState.Success).budgets.toMutableList()
        val index = budgets.indexOf((_state.value as HomeState.Success).currentBudget)

        if (index != -1) {
            val updatedReleases = budgets[index].releases.toMutableList()
            updatedReleases.remove(release)
            budgets[index] = budgets[index].copy(releases = updatedReleases)

            viewModelScope.launch {
                val result = budgetRepository.createRelease(budgets)
                result.onSuccess {
                    _listener.value = HomeListener.ReleasedDeleted
                }
                result.onError {
                    //
                }
            }
        }
    }

    fun getBudgets() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = budgetRepository.getBudgets()
            val user = _user
            val actualMonth = LocalDate.now().month
            if (_selectedMonth == null) {
                _selectedMonth = Month.list.first { month ->
                    month.ordinal == actualMonth.ordinal
                }
            }

            result.onSuccess { items ->
                val currentBudget = items.items.firstOrNull { budget ->
                    val budgetMonth = LocalDate.parse(
                        budget.date,
                        DateTimeFormatter.ofPattern("MM/dd/yyyy")
                    ).month
                    budgetMonth.ordinal == _selectedMonth!!.ordinal
                }
                _state.value = HomeState.Success(
                    user = user,
                    selectedMonth = _selectedMonth!!,
                    budgets = items.items,
                    currentBudget = currentBudget ?: BudgetModel(),
                )
            }
            result.onError { error ->

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
                    DateTimeFormatter.ofPattern("MM/dd/yyyy")
                ).month
                budgetMonth.ordinal == (_state.value as HomeState.Success).selectedMonth.ordinal
            }
            (_state.value as HomeState.Success).currentBudget = selectedBudget ?: BudgetModel()
            _selectedMonth = month
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

}