package com.iagoaf.appfinancas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iagoaf.appfinancas.core.routes.AppRoutes
import com.iagoaf.appfinancas.core.ui.theme.AppFinancasTheme
import com.iagoaf.appfinancas.src.features.auth.presentation.screen.LoginScreen
import com.iagoaf.appfinancas.src.features.auth.presentation.screen.RegisterScreen
import com.iagoaf.appfinancas.src.features.auth.presentation.screen.SplashScreen
import com.iagoaf.appfinancas.src.features.auth.presentation.viewmodel.AuthViewModel
import com.iagoaf.appfinancas.src.features.auth.presentation.viewmodel.LoginViewModel
import com.iagoaf.appfinancas.src.features.auth.presentation.viewmodel.RegisterViewModel
import com.iagoaf.appfinancas.src.features.home.presentation.HomeScreen
import com.iagoaf.appfinancas.src.features.home.presentation.viewModel.HomeViewModel
import com.iagoaf.appfinancas.src.features.monthlyBudget.presentation.MonthlyBudgetScreen
import com.iagoaf.appfinancas.src.features.monthlyBudget.presentation.viewmodel.MonthlyBudgetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppFinancasTheme {
                val authStateViewModel: AuthViewModel = hiltViewModel()
                val isUserLoggedIn = authStateViewModel.isUserLoggedIn.collectAsState()
                when (isUserLoggedIn.value) {
                    null -> SplashScreen()
                    true -> AppNavHost(navController, AppRoutes.HOME, authStateViewModel)
                    false -> AppNavHost(navController, AppRoutes.LOGIN, authStateViewModel)
                }
            }
        }
    }
}


@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    authViewModel: AuthViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppRoutes.LOGIN) {
            val viewModel: LoginViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState().value
            val listener = viewModel.listener.collectAsState().value

            LoginScreen(
                onLogin = { email, password ->
                    viewModel.login(
                        email = email,
                        password = password,
                        onEnd = {
                            authViewModel.checkUserLoggedIn()
                        }
                    )

                },
                onNavigateToRegister = {
                    navController.navigate(AppRoutes.REGISTER)
                },
                state = state,
                listener = listener
            )
        }
        composable(AppRoutes.REGISTER) {

            val viewModel: RegisterViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState().value
            val listener = viewModel.listener.collectAsState().value

            RegisterScreen(
                state = state,
                listener = listener,
                onRegister = { name, email, password ->
                    viewModel.register(
                        name = name,
                        email = email,
                        password = password
                    )
                },
                onBackToLogin = {
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(AppRoutes.HOME) {
            val viewModel: HomeViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState().value

            HomeScreen(
                state,
                onLogout = {
                    authViewModel.logout()
                },
                onChangeMonth = { month ->
                    viewModel.changeMonth(month)
                },
                onLeftChangeMonth = {
                    viewModel.onLeftChangeMonth()
                },
                onRightChangeMonth = {
                    viewModel.onRightChangeMonth()
                },
                onSaveRelease = { release ->
                    viewModel.addRelease(release)
                },
                onClickNewBudget = {

                }
            )
        }
        composable(AppRoutes.MONTHLY_BUDGET) {
            val viewModel: MonthlyBudgetViewModel = hiltViewModel()

            MonthlyBudgetScreen()
        }
    }
}