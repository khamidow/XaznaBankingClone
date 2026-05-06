package uz.mobiler.gita.xaznabankingclone.presentation.screens.splash

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.presentation.screens.language.LanguageScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.main.MainScreen

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
        val pin = pref.getString("pin_code", "") ?: ""
        if (pin.isEmpty()) {
            navigator?.replaceAll(LanguageScreen())
        } else {
            navigator?.replaceAll(MainScreen())
        }
    }
}