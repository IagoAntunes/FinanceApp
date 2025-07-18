package com.iagoaf.appfinancas.src.features.home.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iagoaf.appfinancas.R
import com.iagoaf.appfinancas.core.ui.theme.appTypography
import com.iagoaf.appfinancas.core.ui.theme.gray100
import com.iagoaf.appfinancas.core.ui.theme.gray200
import com.iagoaf.appfinancas.core.ui.theme.gray300
import com.iagoaf.appfinancas.core.ui.theme.gray400
import com.iagoaf.appfinancas.core.ui.theme.gray500
import com.iagoaf.appfinancas.core.ui.theme.gray600
import com.iagoaf.appfinancas.core.ui.theme.gray700
import com.iagoaf.appfinancas.core.ui.theme.green
import com.iagoaf.appfinancas.core.ui.theme.magenta
import com.iagoaf.appfinancas.core.ui.theme.red
import com.iagoaf.appfinancas.core.utils.Month
import com.iagoaf.appfinancas.src.features.auth.domain.model.UserModel
import com.iagoaf.appfinancas.src.features.home.domain.model.BudgetModel
import com.iagoaf.appfinancas.src.features.home.domain.model.ReleaseModel
import com.iagoaf.appfinancas.src.features.home.domain.model.getAvailableBudget
import com.iagoaf.appfinancas.src.features.home.domain.model.getUsedBudget
import com.iagoaf.appfinancas.src.features.home.domain.model.toCurrencyUSD
import com.iagoaf.appfinancas.src.features.home.presentation.components.NewReleaseBottomSheet
import com.iagoaf.appfinancas.src.features.home.presentation.state.HomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onLogout: () -> Unit,
    onChangeMonth: (Month) -> Unit,
    onLeftChangeMonth: () -> Unit,
    onRightChangeMonth: () -> Unit,
    onSaveRelease: (ReleaseModel) -> Unit,
    onClickNewBudget: () -> Unit,
    getBudgets: () -> Unit,
    budgetCreated: State<Boolean>?,
    onCleanKeyBudgetCreated: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(budgetCreated?.value) {
        if (budgetCreated?.value == true) {
            getBudgets()
            onCleanKeyBudgetCreated()
        }
    }

    Scaffold(
        containerColor = gray200,
        floatingActionButton = {
            if (state is HomeState.Success && state.currentBudget.limit.isNotEmpty())
                FloatingActionButton(
                    onClick = {
                        showBottomSheet = true
                    },
                    containerColor = gray700,
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = "Add release",
                        colorFilter = ColorFilter.tint(gray100),
                        modifier = Modifier.size(20.dp)
                    )
                }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        if (showBottomSheet) {
            NewReleaseBottomSheet(
                sheetState = sheetState,
                onDismiss = {
                    showBottomSheet = false
                },
                scope = scope,
                onSaveRelease = { release ->
                    showBottomSheet = false
                    onSaveRelease(release)
                }
            )
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gray100)
                    .padding(horizontal = 20.dp, vertical = 32.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = gray700,
                                shape = CircleShape
                            )
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_background),
                            contentDescription = "User Image"
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            "OLÁ, ${state.user!!.name}!",
                            style = appTypography.titleSm.copy(
                                color = gray700
                            )
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Vamos organizar suas finanças?",
                            style = appTypography.textSm.copy(
                                color = gray500
                            )
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_logout),
                        contentDescription = "Logout",
                        colorFilter = ColorFilter.tint(gray500),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onLogout()
                            }
                    )
                }
            }
            when (state) {
                is HomeState.Error -> {}
                is HomeState.Idle -> {}
                is HomeState.Success -> {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_arrow_left),
                                contentDescription = "Arrow Left",
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable {
                                        onLeftChangeMonth()
                                    },
                                colorFilter = ColorFilter.tint(gray500)
                            )
                            LazyRow(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 20.dp)
                            ) {
                                items(Month.list) { month ->
                                    Box(
                                        modifier = Modifier
                                            .padding(end = 6.dp)
                                            .clickable {
                                                onChangeMonth(month)
                                            }
                                            .padding(horizontal = 8.dp, vertical = 6.dp)
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = month.id,
                                                style = appTypography.titleXs
                                                    .copy(color = if (state.selectedMonth == month) gray700 else gray400),
                                            )
                                            if (state.selectedMonth == month) {
                                                Box(
                                                    modifier = Modifier
                                                        .height(2.dp)
                                                        .width(20.dp)
                                                        .background(magenta)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Image(
                                painter = painterResource(R.drawable.ic_arrow_right),
                                contentDescription = "Arrow Right",
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable {
                                        onRightChangeMonth()
                                    },
                                colorFilter = ColorFilter.tint(gray500)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(gray700)
                                .padding(vertical = 25.dp, horizontal = 20.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Text(
                                            state.selectedMonth.name,
                                            style = appTypography.titleSm,
                                            color = gray100
                                        )
                                        Text(
                                            " / 2025",
                                            style = appTypography.titleXs,
                                            color = gray400
                                        )
                                    }
                                    Image(
                                        painter = painterResource(R.drawable.ic_settings),
                                        contentDescription = "Settings",
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                                Spacer(Modifier.height(32.dp))
                                Text(
                                    "Orçamento disponível",
                                    style = appTypography.textSm,
                                    color = gray400
                                )
                                Spacer(Modifier.height(8.dp))
                                if (state.currentBudget.limit.isEmpty()) {
                                    OutlinedButton(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(6.dp),
                                        onClick = onClickNewBudget,
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = magenta
                                        ),
                                        border = BorderStroke(
                                            width = 1.dp,
                                            color = magenta
                                        )
                                    ) {
                                        Text(
                                            "Definir orçamento",
                                            style = appTypography.buttonSm,
                                            color = magenta,
                                        )
                                    }
                                } else {
                                    Text(
                                        "\$ ${
                                            state.currentBudget.getAvailableBudget().toCurrencyUSD()
                                        }",
                                        style = appTypography.titleLg,
                                        color = gray100
                                    )
                                }
                                Spacer(Modifier.height(20.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            "Usado",
                                            style = appTypography.textXs,
                                            color = gray400
                                        )
                                        if (state.currentBudget.limit.isNotEmpty())
                                            Text(
                                                "\$ ${
                                                    state.currentBudget.getUsedBudget()
                                                        .toCurrencyUSD()
                                                }",
                                                style = appTypography.textSm,
                                                color = gray100
                                            )
                                    }
                                    Column {
                                        Text(
                                            "Limite",
                                            style = appTypography.textXs,
                                            color = gray400
                                        )
                                        if (state.currentBudget.limit.isNotEmpty()) {
                                            Text(
                                                "\$ ${state.currentBudget.limit.toCurrencyUSD()}",
                                                style = appTypography.textSm,
                                                color = gray100
                                            )
                                        } else {
                                            Text(
                                                "∞",
                                                style = appTypography.textSm.copy(
                                                    fontSize = 22.sp
                                                ),
                                                color = gray100,
                                                textAlign = TextAlign.Center,
                                            )
                                        }

                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(gray100)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "LANÇAMENTOS",
                                        style = appTypography.title2Xs,
                                        color = gray500,

                                        )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(gray300)
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            state.currentBudget.releases.size.toString(),
                                            style = appTypography.titleXs,
                                            color = gray600
                                        )
                                    }
                                }
                                HorizontalDivider(
                                    color = gray300
                                )
                                if (state.currentBudget.releases.isEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(20.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row {
                                            Image(
                                                painter = painterResource(R.drawable.ic_note),
                                                contentDescription = "No Releases Icon",
                                                modifier = Modifier.size(26.dp),
                                                colorFilter = ColorFilter.tint(gray400)
                                            )
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(
                                                "Você ainda não registrou despesas ou receitas neste mês",
                                                style = appTypography.textXs,
                                                color = gray400
                                            )
                                        }
                                    }
                                } else {
                                    LazyColumn(
                                        modifier = Modifier.padding(
                                            horizontal = 20.dp,
                                            vertical = 16.dp
                                        )
                                    ) {
                                        itemsIndexed(state.currentBudget.releases) { i, release ->
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .clip(RoundedCornerShape(12.dp))
                                                            .background(gray200)
                                                            .border(
                                                                width = 1.dp,
                                                                color = gray300,
                                                                shape = RoundedCornerShape(12.dp)
                                                            )
                                                    ) {
                                                        Image(
                                                            painter = painterResource(R.drawable.ic_arrow_left),
                                                            contentDescription = "Release Icon",
                                                            modifier = Modifier
                                                                .padding(8.dp)
                                                                .size(20.dp),
                                                            colorFilter = ColorFilter.tint(magenta)
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.width(12.dp))
                                                    Column {
                                                        Text(
                                                            release.title,
                                                            style = appTypography.textSm,
                                                            color = gray700
                                                        )
                                                        Text(
                                                            release.date,
                                                            style = appTypography.textXs,
                                                            color = gray500
                                                        )
                                                    }
                                                }
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        "R\$",
                                                        style = appTypography.textXs,
                                                        color = gray700
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text(
                                                        release.value,
                                                        style = appTypography.titleMd,
                                                        color = gray700
                                                    )
                                                    Spacer(modifier = Modifier.width(2.dp))
                                                    if (release.isPositive) {
                                                        Image(
                                                            painter = painterResource(R.drawable.ic_arrow_up),
                                                            colorFilter = ColorFilter.tint(green),
                                                            modifier = Modifier.size(12.dp),
                                                            contentDescription = ""
                                                        )
                                                    }
                                                    if (!release.isPositive) {
                                                        Image(
                                                            painter = painterResource(R.drawable.ic_arrow_down),
                                                            colorFilter = ColorFilter.tint(red),
                                                            modifier = Modifier.size(12.dp),
                                                            contentDescription = ""
                                                        )
                                                    }

                                                    Spacer(modifier = Modifier.width(10.dp))
                                                    Image(
                                                        painter = painterResource(R.drawable.ic_delete),
                                                        colorFilter = ColorFilter.tint(magenta),
                                                        modifier = Modifier.size(16.dp),
                                                        contentDescription = "Delete release"
                                                    )
                                                }
                                            }
                                            if (i < state.currentBudget.releases.size - 1)
                                                HorizontalDivider(
                                                    color = gray300,
                                                    modifier = Modifier.padding(top = 12.dp)
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
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState.Success(
            user = UserModel(
                uid = "",
                name = "Iago",
                email = "a"
            ),
            budgets = emptyList(),
            currentBudget = BudgetModel(
                availableBudget = "",
                budgetUsed = "",
                date = "",
                limit = "",
                releases = listOf(
                    ReleaseModel(
                        category = "",
                        date = "08/07/2025",
                        isPositive = false,
                        value = "450,67",
                        title = "Mercado"
                    )
                )
            ),
        ),
        onLogout = {

        },
        onChangeMonth = {},
        onLeftChangeMonth = {},
        onRightChangeMonth = {},
        onSaveRelease = {},
        onClickNewBudget = {},
        getBudgets = {},
        onCleanKeyBudgetCreated = {},
        budgetCreated = remember { mutableStateOf(false) }
    )
}

@Preview
@Composable
private fun HomeScreenEmptyReleasesPreview() {
    HomeScreen(
        state = HomeState.Success(
            user = UserModel(
                uid = "",
                name = "Iago",
                email = "a"
            ),
            budgets = emptyList(),
            currentBudget = BudgetModel(
                availableBudget = "",
                budgetUsed = "",
                date = "",
                limit = "",
                releases = emptyList()
            ),
        ),
        onLogout = {},
        onChangeMonth = {},
        onLeftChangeMonth = {},
        onRightChangeMonth = {},
        onSaveRelease = {},
        onClickNewBudget = {},
        getBudgets = {},
        onCleanKeyBudgetCreated = {},
        budgetCreated = remember { mutableStateOf(false) }
    )
}