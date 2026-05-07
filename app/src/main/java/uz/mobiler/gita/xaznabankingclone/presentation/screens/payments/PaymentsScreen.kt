package uz.mobiler.gita.xaznabankingclone.presentation.screens.payments

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class PaymentsScreen: Screen {
    @Composable
    override fun Content() {
        Text("PAYMENTS", modifier = Modifier.safeDrawingPadding())
    }
}