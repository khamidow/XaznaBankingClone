package uz.mobiler.gita.xaznabankingclone.presentation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberImagePainter
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.payments.PaymentsScreen

class PaymentsTab: Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 2u,
            title = stringResource(R.string.payment),
            icon = rememberImagePainter(R.drawable.ic_wallet)
        )

    @Composable
    override fun Content() {
        PaymentsScreen().Content()
    }
}