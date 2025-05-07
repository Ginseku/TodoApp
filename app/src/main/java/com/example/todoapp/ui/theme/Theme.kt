package com.example.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.todoapp.ui.theme.BackgroundColor
import com.example.todoapp.ui.theme.ButtonColor
import com.example.todoapp.ui.theme.DisabledColor
import com.example.todoapp.ui.theme.SurfaceColor
import com.example.todoapp.ui.theme.TextColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif, // Использует стандартный шрифт Roboto
        fontWeight = FontWeight.Normal
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold
    )
)

// Светлая тема
private val LightColorScheme = darkColorScheme( // Используем darkColorScheme, так как дизайн одинаковый
    primary = ButtonColor, // Основной цвет (кнопки, акцент)
    onPrimary = TextColor, // Цвет текста на кнопках
    background = BackgroundColor, // Фон
    onBackground = TextColor, // Текст на фоне
    surface = SurfaceColor, // Цвет карточек и поверхностей
    onSurface = TextColor, // Текст на карточках
    secondary = DisabledColor, // Неактивные элементы
)

// Тёмная тема (та же палитра)
private val DarkColorScheme = LightColorScheme

@Composable
fun AppTheme(
    darkTheme: Boolean = true, // Можно менять, если нужна поддержка системной темы
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
