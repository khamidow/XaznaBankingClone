package uz.mobiler.gita.xaznabankingclone.presentation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberImagePainter
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.transfers.TransfersScreen

class TransfersTab: Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 1u,
            title = stringResource(R.string.transfers),
            icon = rememberImagePainter(R.drawable.ic_transfers)
        )

    @Composable
    override fun Content() {
        TransfersScreen().Content()
    }
}