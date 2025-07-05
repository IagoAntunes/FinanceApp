package com.iagoaf.appfinancas.src.features.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iagoaf.appfinancas.R
import com.iagoaf.appfinancas.core.ui.theme.appTypography
import com.iagoaf.appfinancas.core.ui.theme.gray100
import com.iagoaf.appfinancas.core.ui.theme.gray200
import com.iagoaf.appfinancas.core.ui.theme.gray500
import com.iagoaf.appfinancas.core.ui.theme.gray700
import com.iagoaf.appfinancas.src.features.home.presentation.state.HomeState

@Composable
fun HomeScreen(
    state: HomeState,
    onLogout: () -> Unit,
) {
    Scaffold(
        containerColor = gray200,
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
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
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState.Idle(
            user = null
        ),
        onLogout = {

        }

    )
}