package uz.mobiler.gita.xaznabankingclone.presentation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberImagePainter
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.home.HomeScreen

class HomeTab: Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 0u,
            title = stringResource(R.string.home),
            icon = rememberImagePainter(R.drawable.ic_pro)
        )

    @Composable
    override fun Content() {
        HomeScreen().Content()
    }
}