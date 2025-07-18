package com.iagoaf.appfinancas.src.features.home.domain.model

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
        val used = BigDecimal(this.budgetUsed.replace(",", "."))
        val lim = BigDecimal(this.limit.replace(",", "."))

        val result = lim - used

        val symbols = DecimalFormatSymbols(Locale.US)
        val decimalFormat = DecimalFormat("#,##0.00", symbols)
        decimalFormat.format(result)
    } catch (e: Exception) {
        "0.00"
    }
}

fun BudgetModel.getUsedBudget(): String {
    return try {
        val total = releases.fold(BigDecimal.ZERO) { acc, release ->
            val value = BigDecimal(release.value.replace(",", "."))
            if (release.isPositive) acc + value else acc - value
        }

        val symbols = DecimalFormatSymbols(Locale.US)
        val decimalFormat = DecimalFormat("#,##0.00", symbols)
        decimalFormat.format(total)
    } catch (e: Exception) {
        "0.00"
    }
}


fun BudgetModel.isTwoMonthsAhead(): Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
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
