package uz.mobiler.gita.xaznabankingclone.presentation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberImagePainter
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.monitoring.MonitoringScreen

class MonitoringTab: Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 3u,
            title = stringResource(R.string.monitoring),
            icon = rememberImagePainter(R.drawable.ic_monitoring)
        )

    @Composable
    override fun Content() {
        MonitoringScreen().Content()
    }
}