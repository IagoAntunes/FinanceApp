package com.iagoaf.appfinancas.src.features.monthlyBudget.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iagoaf.appfinancas.R
import com.iagoaf.appfinancas.core.components.CTextField
import com.iagoaf.appfinancas.core.components.CTextFieldContainer
import com.iagoaf.appfinancas.core.ui.theme.appTypography
import com.iagoaf.appfinancas.core.ui.theme.gray100
import com.iagoaf.appfinancas.core.ui.theme.gray200
import com.iagoaf.appfinancas.core.ui.theme.gray300
import com.iagoaf.appfinancas.core.ui.theme.gray400
import com.iagoaf.appfinancas.core.ui.theme.gray500
import com.iagoaf.appfinancas.core.ui.theme.gray600
import com.iagoaf.appfinancas.core.ui.theme.gray700
import com.iagoaf.appfinancas.core.ui.theme.latoBold
import com.iagoaf.appfinancas.core.ui.theme.latoRegular
import com.iagoaf.appfinancas.core.ui.theme.magenta
import com.iagoaf.appfinancas.core.ui.theme.red
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel
import com.iagoaf.appfinancas.src.features.home.domain.model.isTwoMonthsAhead
import com.iagoaf.appfinancas.src.features.home.domain.model.toCurrencyUSD
import com.iagoaf.appfinancas.src.features.monthlyBudget.presentation.listener.MonthlyBudgetListener
import com.iagoaf.appfinancas.src.features.monthlyBudget.presentation.state.MonthlyBudgetState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyBudgetScreen(
    onBack: () -> Unit,
    onBackBudgetCreated: () -> Unit,
    onDeleteBudget: (BudgetModel) -> Unit,
    onAddBudget: (String) -> Unit,
    state: MonthlyBudgetState,
    listener: MonthlyBudgetListener
) {

    LaunchedEffect(listener) {
        when (listener) {
            MonthlyBudgetListener.BudgetCreated -> {
                onBackBudgetCreated()
            }

            MonthlyBudgetListener.Idle -> {}
            MonthlyBudgetListener.BudgetDeleted -> {

            }
        }
    }

    Scaffold(
        containerColor = gray200,
    ) { innerPadding ->

        val value = remember { mutableStateOf("") }

        when (state) {
            is MonthlyBudgetState.Idle -> {
                Box(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is MonthlyBudgetState.Success -> {
                Column(modifier = Modifier.padding(innerPadding)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(gray100)
                            .padding(vertical = 27.dp, horizontal = 20.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_arrow_left),
                                contentDescription = "Arrow Back",
                                colorFilter = ColorFilter.tint(gray500),
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable(onClick = onBack)
                            )
                            Column {
                                Text(
                                    "MONTHLY BUDGETS",
                                    style = appTypography.titleSm,
                                    color = gray700
                                )
                                Text(
                                    "Organize your monthly spending limits",
                                    style = appTypography.textSm,
                                    color = gray500
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                                .background(gray100)
                                .padding(vertical = 14.dp, horizontal = 20.dp)
                        ) {
                            Column {
                                Text(
                                    "NEW BUDGET",
                                    style = appTypography.title2Xs,
                                    color = gray500
                                )
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    color = gray300
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    CTextFieldContainer(
                                        value = (state.date.month.ordinal + 1).toString()
                                            .padStart(2, '0'),
                                        prefix = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_date),
                                                contentDescription = null,
                                                tint = gray600,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        },
                                        onClick = {

                                        },
                                        modifier = Modifier
                                            .weight(1F)
                                    )
                                    CTextField(
                                        value = value.value,
                                        prefix = {
                                            Text(
                                                "\$",
                                                style = appTypography.input,
                                                color = gray600,
                                            )
                                        },
                                        onValueChange = { text ->
                                            value.value = text
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number
                                        ),
                                        modifier = Modifier.weight(2f),
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = {
                                        onAddBudget(value.value)
                                    },
                                    enabled = value.value.isNotEmpty(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = magenta
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentPadding = PaddingValues(
                                        vertical = 12.dp
                                    )
                                ) {
                                    Text(
                                        "Add",
                                        style = appTypography.buttonMd,
                                        color = gray100
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                                .background(gray100)
                                .padding(vertical = 14.dp, horizontal = 20.dp)
                        ) {
                            Column {
                                Text(
                                    "REGISTERED BUDGETS",
                                    style = appTypography.title2Xs,
                                    color = gray500
                                )
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    color = gray300
                                )
                                LazyColumn {
                                    items(
                                        state.budgets
                                    ) { budget ->
                                        val isTwoMonthsAhead = budget.isTwoMonthsAhead()
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    bottom = 12.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.ic_date),
                                                    contentDescription = "Date",
                                                    colorFilter = ColorFilter.tint(if (!isTwoMonthsAhead) gray400 else gray700),
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Text(
                                                    LocalDate.parse(
                                                        budget.date,
                                                        DateTimeFormatter.ofPattern("MM/dd/yyyy")
                                                    ).month.name,
                                                    style = TextStyle(
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        fontFamily = latoBold
                                                    ),
                                                    color = if (!isTwoMonthsAhead) gray400 else gray700
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    state.year,
                                                    style = TextStyle(
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.W500,
                                                        fontFamily = latoRegular
                                                    ),
                                                    color = if (!isTwoMonthsAhead) gray400 else gray600
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Text(
                                                    "\$",
                                                    style = appTypography.textXs.copy(
                                                        fontSize = 12.sp
                                                    ),
                                                    color = if (!isTwoMonthsAhead) gray400 else gray700
                                                )
                                                Spacer(modifier = Modifier.width(2.dp))
                                                Text(
                                                    budget.limit.toCurrencyUSD(),
                                                    style = appTypography.titleMd.copy(
                                                        fontSize = 14.sp,
                                                    ),
                                                    color = if (!isTwoMonthsAhead) gray400 else gray700
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                if (isTwoMonthsAhead)
                                                    Image(
                                                        painter = painterResource(R.drawable.ic_delete),
                                                        contentDescription = "Delete",
                                                        colorFilter = ColorFilter.tint(red),
                                                        modifier = Modifier
                                                            .size(14.dp)
                                                            .clickable {
                                                                onDeleteBudget(budget)
                                                            }
                                                    )
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            MonthlyBudgetState.Loading -> Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }


    }
}

@Preview
@Composable
private fun MonthlyBudgetPreview() {
    MonthlyBudgetScreen(
        state = MonthlyBudgetState.Success(
            date = LocalDate.of(2025, 1, 1),
            formattedDate = "01/05/2025",
            budgets = listOf(
                BudgetModel(
                    availableBudget = "",
                    date = "01/01/2025",
                    limit = "1000",
                    budgetUsed = "",
                    releases = emptyList()
                ),
                BudgetModel(
                    availableBudget = "",
                    date = "01/01/2025",
                    limit = "1000",
                    budgetUsed = "",
                    releases = emptyList()
                ),
                BudgetModel(
                    availableBudget = "",
                    date = "01/01/2025",
                    limit = "1000",
                    budgetUsed = "",
                    releases = emptyList()
                )
            ),
            monthName = "Janeiro",
            year = "2023"
        ),
        onBack = {},
        onDeleteBudget = {},
        onAddBudget = {},
        listener = MonthlyBudgetListener.Idle,
        onBackBudgetCreated = {}
    )
}