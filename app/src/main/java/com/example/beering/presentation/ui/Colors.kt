package com.example.beering.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.beering.presentation.ui.theme.PurpleGrey80

data class ScreenBackground(
    val color: Color = PurpleGrey80
)

val LocalScreenBackground = compositionLocalOf { ScreenBackground() }

val MaterialTheme.screenBackground: ScreenBackground
    @Composable
    @ReadOnlyComposable
    get() = LocalScreenBackground.current