package com.iagoaf.appfinancas.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.iagoaf.appfinancas.R

data class AppTypography(
    val titleLg: TextStyle,
    val titleMd: TextStyle,
    val titleSm: TextStyle,
    val titleXs: TextStyle,
    val title2Xs: TextStyle,
    val textSm: TextStyle,
    val textXs: TextStyle,
    val input: TextStyle,
    val buttonMd: TextStyle,
    val buttonSm: TextStyle,
)

val latoRegular = FontFamily(
    Font(R.font.lato_regular)
)

val latoBold = FontFamily(
    Font(R.font.lato_bold)
)

val latoBlack = FontFamily(
    Font(R.font.lato_black)
)

val appTypography = AppTypography(
    titleLg = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Black,
        fontFamily = latoBlack
    ),
    titleMd = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = latoBold
    ),
    titleSm = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = latoBold
    ),
    titleXs = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = latoBold
    ),
    title2Xs = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = latoBold
    ),
    textSm = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = latoRegular
    ),
    textXs = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = latoRegular
    ),
    input = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = latoRegular
    ),
    buttonMd = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = latoBold
    ),
    buttonSm = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = latoBold
    )
)

val Typography = Typography()