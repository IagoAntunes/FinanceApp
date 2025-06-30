package com.iagoaf.appfinancas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iagoaf.appfinancas.core.routes.AppRoutes
import com.iagoaf.appfinancas.core.ui.theme.AppFinancasTheme
import com.iagoaf.appfinancas.src.features.auth.presentation.screen.LoginScreen
import com.iagoaf.appfinancas.src.features.auth.presentation.screen.RegisterScreen
import com.iagoaf.appfinancas.src.features.auth.presentation.viewmodel.LoginViewModel
import com.iagoaf.appfinancas.src.features.auth.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppFinancasTheme {
                NavHost(
                    navController = navController,
                    startDestination = AppRoutes.LOGIN
                ) {
                    composable(AppRoutes.LOGIN) {
                        val viewModel: LoginViewModel = hiltViewModel()

                        LoginScreen(
                            onLogin = { email, password ->

                            },
                            onNavigateToRegister = {
                                navController.navigate(AppRoutes.REGISTER)
                            }
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
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFinancasTheme {
        Greeting("Android")
    }
}