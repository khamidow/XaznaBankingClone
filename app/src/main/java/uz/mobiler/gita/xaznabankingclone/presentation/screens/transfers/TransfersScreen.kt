package uz.mobiler.gita.xaznabankingclone.presentation.screens.transfers

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class TransfersScreen: Screen {
    @Composable
    override fun Content() {
        Text("TRANSFERS", modifier = Modifier.safeDrawingPadding())
    }
}