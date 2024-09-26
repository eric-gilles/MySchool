package com.example.myschool.ui.theme

import android.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable


@Stable
class MySchoolTheme(
    val colors: Color,
    val typography: MyTypography
)

@Composable
fun MySchoolTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = PrimaryColor,
            secondary = SecondaryColor,
            background = BackgroundColor,
            surface = SurfaceColor,
            error = ErrorColor,
            onPrimary = OnPrimaryColor,
            onSecondary = OnSecondaryColor,
            onBackground = OnBackgroundColor,
            onSurface = OnSurfaceColor,
            onError = OnErrorColor
        ),
        typography = MyTypography().typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
