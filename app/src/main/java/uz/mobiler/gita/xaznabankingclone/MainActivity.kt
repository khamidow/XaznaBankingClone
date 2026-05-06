package uz.mobiler.gita.xaznabankingclone

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import uz.mobiler.gita.xaznabankingclone.appSettings.SettingsViewModel
import uz.mobiler.gita.xaznabankingclone.presentation.screens.infoAdvantage.InfoAdvantageScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.language.LanguageScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.login.LoginScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.register.RegisterScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.settings.SettingsScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.splash.SplashScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.verify.VerifyScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val pref = getSharedPreferences("settings", Context.MODE_PRIVATE)
            if (pref.getBoolean("screenshot_restrict", false)) {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
            val theme by settingsViewModel.theme.collectAsState()
            val langCode by settingsViewModel.language.collectAsState()
            val localizedContext = rememberLocalizedContext(langCode)
            XaznaBankingCloneTheme(theme) {
                CompositionLocalProvider(LocalContext provides localizedContext) {
                    Navigator(SplashScreen())
                }
            }
        }
    }
}

@SuppressLint("LocalContextConfigurationRead")
@Composable
fun rememberLocalizedContext(langCode: String): Context {
    val activity = LocalContext.current
    return remember(langCode) {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val config = Configuration(activity.resources.configuration)
        config.setLocale(locale)
        val localizedResources = activity.createConfigurationContext(config).resources
        object : ContextWrapper(activity) {
            override fun getResources() = localizedResources
        }
    }
}

