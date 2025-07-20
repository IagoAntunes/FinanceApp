package com.iagoaf.appfinancas.src.features.home.domain.model

import android.util.Log
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

data class BudgetModel(
    val availableBudget: String = "",
    val budgetUsed: String = "",
    val date: String = "",
    val limit: String = "",
    val releases: List<ReleaseModel> = emptyList()
)

fun String.toCurrencyUSD(): String {
    return try {
        val value = BigDecimal(this.replace(",", "."))
        val symbols = DecimalFormatSymbols(Locale.US)
        val decimalFormat = DecimalFormat("#,##0.00", symbols)
        decimalFormat.format(value)
    } catch (e: Exception) {
        "$0.00"
    }
}

fun BudgetModel.getAvailableBudget(): String {
    return try {
        val limit = BigDecimal(this.limit.replace(",", "."))
        val totalDeposits = releases.filter { it.positive }
            .fold(BigDecimal.ZERO) { acc, release ->
                acc + BigDecimal(release.value.replace(",", "."))
            }
        val totalExpenses = releases.filter { !it.positive }
            .fold(BigDecimal.ZERO) { acc, release ->
                acc + BigDecimal(release.value.replace(",", "."))
            }

        val available = limit + totalDeposits - totalExpenses

        val symbols = DecimalFormatSymbols(Locale.US)
        val decimalFormat = DecimalFormat("#,##0.00", symbols)
        decimalFormat.format(available)
    } catch (e: Exception) {
        "0.00"
    }
}

fun BudgetModel.getUsedBudget(): String {
    return try {
        val totalDeposits = releases.filter { it.positive }
            .fold(BigDecimal.ZERO) { acc, release ->
                acc + BigDecimal(release.value.replace(",", "."))
            }
        val totalExpenses = releases.filter { !it.positive }
            .fold(BigDecimal.ZERO) { acc, release ->
                acc + BigDecimal(release.value.replace(",", "."))
            }

        val used = if (totalExpenses > totalDeposits) totalExpenses - totalDeposits else BigDecimal.ZERO

        val symbols = DecimalFormatSymbols(Locale.US)
        val decimalFormat = DecimalFormat("#,##0.00", symbols)
        decimalFormat.format(used)
    } catch (e: Exception) {
        "0.00"
    }
}




fun BudgetModel.isTwoMonthsAhead(): Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val budgetDate = LocalDate.parse(this.date, formatter)
        val currentDate = LocalDate.now()

        val monthsBetween = ChronoUnit.MONTHS.between(
            YearMonth.from(currentDate),
            YearMonth.from(budgetDate)
        )

        monthsBetween >= 2
    } catch (e: Exception) {
        false
    }
}
