package ru.faimizufarov.core.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.faimizufarov.core.R

private val mainColorScheme =
    lightColorScheme(
        primary = Colors.leaf,
        onPrimary = Colors.white,
        secondary = Colors.turtleGreen,
        onSecondary = Colors.white,
        surface = Colors.white,
        surfaceVariant = Colors.lightGray,
        onSurface = Colors.black,
        background = Colors.white,
    )

private val typography =
    Typography(
        bodySmall =
            TextStyle(
                fontSize = 12.sp,
            ),
        bodyMedium =
            TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                color = Colors.black70,
            ),
        headlineMedium =
            TextStyle(
                fontSize = 21.sp,
                fontFamily =
                    FontFamily(
                        Font(
                            R.font.officina_sans_extra_bold_c,
                            FontWeight.ExtraBold,
                        ),
                    ),
                color = Colors.white,
            ),
        labelSmall =
            TextStyle(
                fontSize = 14.sp,
                color = Colors.black38,
            ),
    )

@Composable
fun HelpTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = mainColorScheme.background.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = mainColorScheme,
        typography = typography,
        content = content,
    )
}
