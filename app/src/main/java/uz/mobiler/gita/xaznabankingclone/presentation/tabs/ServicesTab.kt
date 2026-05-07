package uz.mobiler.gita.xaznabankingclone.presentation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberImagePainter
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.services.ServicesScreen

class ServicesTab: Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 4u,
            title = stringResource(R.string.services),
            icon = rememberImagePainter(R.drawable.ic_services)
        )

    @Composable
    override fun Content() {
        ServicesScreen().Content()
    }
}