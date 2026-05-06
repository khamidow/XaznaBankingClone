package uz.mobiler.gita.xaznabankingclone.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption

private val DarkColorScheme = darkColorScheme(
    primary = enabled,
    onPrimary = white,
    onPrimaryFixed = lightWhite,
    primaryContainer = darkGreen,
    secondary = disabled,
    onSecondary = disabledText,
    tertiary = lightGradient,
    tertiaryFixed = darkGradient,
    error = redColor,
    background = grayBackground,
    tertiaryContainer = black,
    primaryFixedDim = grayBackground,
    onBackground = dark,
    tertiaryFixedDim =grayBackground
)

private val LightColorScheme = lightColorScheme(
    primary = enabled,
    onPrimary = black,
    onPrimaryFixed = lightWhite,
    primaryContainer = lightGray,
    secondary = disabled,
    onSecondary = disabledText,
    tertiary = lightGradient,
    tertiaryFixed = darkGradient,
    error = redColor,
    background = white,
    tertiaryContainer = white,
    primaryFixedDim = white,
    onBackground = light,
    tertiaryFixedDim = lightBackground
)
private val DarkBlueColorScheme = darkColorScheme(
    primary = bluePrimary,
    onPrimary = white,
    onPrimaryFixed = lightWhite,
    primaryContainer = blueDark,
    secondary = blueDisabled,
    onSecondary = blueDisabledText,
    tertiary = blueLight,
    tertiaryFixed = blueGradientDark,
    error = redColor,
    background = blueBackgroundDark,
    tertiaryContainer = black,
    primaryFixedDim = blueBackgroundDark,
    onBackground = dark,
    tertiaryFixedDim = blueBackgroundDark
)

@Composable
fun XaznaBankingCloneTheme(
    themeType: AppThemeOption = AppThemeOption.SYSTEM,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val isDarkMode = isSystemInDarkTheme()

    val colorScheme = when (themeType) {
        AppThemeOption.DARK -> DarkColorScheme
        AppThemeOption.LIGHT -> LightColorScheme
        AppThemeOption.CUSTOM -> DarkBlueColorScheme
        AppThemeOption.SYSTEM -> {
            if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (isDarkMode) DarkColorScheme else LightColorScheme
            } else if (isDarkMode) DarkColorScheme
            else LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}