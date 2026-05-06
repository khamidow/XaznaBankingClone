package uz.mobiler.gita.xaznabankingclone.presentation.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.presentation.screens.settings.SettingsScreen

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Box(modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()) {
            Text(
                "Settings Screen",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navigator?.push(SettingsScreen())
                    })
        }
    }
}