package com.example.myschool.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MyTypography(
    val typography: Typography = Typography(
        headlineLarge = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        ),
        headlineMedium = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        bodyLarge = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        ),
        labelLarge = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    )
)
