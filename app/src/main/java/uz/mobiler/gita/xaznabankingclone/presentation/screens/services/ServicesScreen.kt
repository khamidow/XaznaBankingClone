package uz.mobiler.gita.xaznabankingclone.presentation.screens.services

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class ServicesScreen: Screen {
    @Composable
    override fun Content() {
        Text("SERVICES", modifier = Modifier.safeDrawingPadding())
    }
}