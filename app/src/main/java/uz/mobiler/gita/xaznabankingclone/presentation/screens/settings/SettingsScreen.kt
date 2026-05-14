package uz.mobiler.gita.xaznabankingclone.presentation.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.appSettings.SettingsViewModel
import uz.mobiler.gita.xaznabankingclone.presentation.items.BottomItem
import uz.mobiler.gita.xaznabankingclone.presentation.items.SettingsItem
import uz.mobiler.gita.xaznabankingclone.presentation.items.ThemeRadioItem
import uz.mobiler.gita.xaznabankingclone.presentation.screens.appInfo.AppInfoScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.notification.NotificationScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.security.SecurityScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.widgetSettings.WidgetScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme

class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        SettingsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenContent() {
    val navigator = LocalNavigator.current
    val localContext = LocalContext.current
    val viewModel: SettingsViewModel = hiltViewModel<SettingsViewModel>()

    val currentTheme by viewModel.theme.collectAsState()
    val currentLanguage by viewModel.language.collectAsState()

    val themeSheetState = rememberModalBottomSheetState(true)
    var showThemeSheet by remember { mutableStateOf(false) }

    val languageSheetState = rememberModalBottomSheetState(true)
    var showLanguageSheet by remember { mutableStateOf(false) }

    val selectedTheme = when (currentTheme) {
        AppThemeOption.LIGHT -> 1
        AppThemeOption.DARK -> 2
        AppThemeOption.SYSTEM -> 3
        AppThemeOption.CUSTOM -> 4
    }
    val selectedLanguage = when (currentLanguage) {
        "ru" -> 1
        "uz" -> 2
        "en" -> 3
        else -> 2
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Box(
                    modifier = Modifier
                        .padding(18.dp)
                        .size(28.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimaryFixed,
                            RoundedCornerShape(6.dp)
                        )
                        .clickable {
                            navigator?.pop()
                        }
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed)
                    )
                }

                Text(
                    text = stringResource(R.string.settings),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(28.dp))

            SettingsItem(R.drawable.ic_security, stringResource(R.string.security)) {
                navigator?.push(SecurityScreen())
            }
            SettingsItem(R.drawable.ic_notification, stringResource(R.string.notification)) {
                navigator?.push(NotificationScreen())
            }
            SettingsItem(R.drawable.ic_widget, stringResource(R.string.widget_settings)) {
                navigator?.push(WidgetScreen())
            }
            SettingsItem(R.drawable.ic_theme_moon, stringResource(R.string.app_theme)) {
                showThemeSheet = true
            }
            SettingsItem(R.drawable.ic_language, stringResource(R.string.language)) {
                showLanguageSheet = true
            }
            SettingsItem(R.drawable.ic_info, stringResource(R.string.about_app)) {
                navigator?.push(AppInfoScreen())
            }

            Spacer(modifier = Modifier.weight(1f))

            BottomItem(stringResource(R.string.public_offer)) {

            }
        }

        if (showThemeSheet) {
            ModalBottomSheet(
                onDismissRequest = { showThemeSheet = false },
                sheetState = themeSheetState,
                dragHandle = null
            ) {
                CompositionLocalProvider(LocalContext provides localContext) {
                    val options = listOf(
                        ThemeOption(1, stringResource(R.string.light), R.drawable.ic_light),
                        ThemeOption(2, stringResource(R.string.dark), R.drawable.ic_theme_moon),
                        ThemeOption(3, stringResource(R.string.system), R.drawable.ic_setting),
                        ThemeOption(4, stringResource(R.string.custom), R.drawable.ic_custom_theme)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(18.dp)
                    ) {
                        Spacer(Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                stringResource(R.string.app_theme),
                                fontSize = 21.sp,
                                fontWeight = FontWeight.W600,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.weight(1f))
                            Image(
                                painter = painterResource(R.drawable.ic_close),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { showThemeSheet = false }
                            )
                        }
                        HorizontalDivider(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 18.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            thickness = 1.dp
                        )
                        options.forEachIndexed { index, option ->
                            ThemeRadioItem(
                                option = option,
                                selected = selectedTheme == option.id,
                                onClick = {
                                    viewModel.setTheme(
                                        when (index) {
                                            0 -> AppThemeOption.LIGHT
                                            1 -> AppThemeOption.DARK
                                            2 -> AppThemeOption.SYSTEM
                                            3 -> AppThemeOption.CUSTOM
                                            else -> AppThemeOption.SYSTEM
                                        }
                                    )
                                    showThemeSheet = false
                                }
                            )

                            if (index != options.lastIndex) {
                                HorizontalDivider(
                                    Modifier
                                        .fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    thickness = 1.dp
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showLanguageSheet) {
            ModalBottomSheet(
                onDismissRequest = { showLanguageSheet = false },
                sheetState = languageSheetState,
                dragHandle = null
            ) {
                CompositionLocalProvider(LocalContext provides localContext) {
                    val options = listOf(
                        ThemeOption(1, "Русский", R.drawable.ic_rus),
                        ThemeOption(2, "O'zbekcha", R.drawable.ic_uzb),
                        ThemeOption(3, "English", R.drawable.ic_eng)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(18.dp)
                    ) {
                        Spacer(Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                stringResource(R.string.language),
                                fontSize = 21.sp,
                                fontWeight = FontWeight.W600,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.weight(1f))
                            Image(
                                painter = painterResource(R.drawable.ic_close),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { showLanguageSheet = false }
                            )
                        }
                        HorizontalDivider(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 18.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            thickness = 1.dp
                        )
                        options.forEachIndexed { index, option ->
                            ThemeRadioItem(
                                option = option,
                                selected = selectedLanguage == option.id,
                                onClick = {
                                    viewModel.setLanguage(
                                        when (index) {
                                            0 -> "ru"
                                            1 -> "uz"
                                            2 -> "en"
                                            else -> "uz"
                                        }
                                    )
                                    showLanguageSheet = false
                                }
                            )

                            if (index != options.lastIndex) {
                                HorizontalDivider(
                                    Modifier
                                        .fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    thickness = 1.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenContentPreview() {
    XaznaBankingCloneTheme(themeType = AppThemeOption.DARK) {
        SettingsScreenContent()
    }
}

data class ThemeOption(
    val id: Int,
    val title: String,
    val icon: Int
)
