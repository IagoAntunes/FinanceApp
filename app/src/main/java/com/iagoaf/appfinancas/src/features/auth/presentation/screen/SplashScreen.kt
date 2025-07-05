package com.iagoaf.appfinancas.src.features.auth.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iagoaf.appfinancas.core.ui.theme.gray100

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Scaffold(
        containerColor = gray100
    ) { innerPadding ->
        CircularProgressIndicator(modifier = Modifier.padding(innerPadding))
    }
}