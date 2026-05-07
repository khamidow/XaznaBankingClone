package uz.mobiler.gita.xaznabankingclone.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import uz.mobiler.gita.xaznabankingclone.presentation.tabNavigation.TabNavigationItem
import uz.mobiler.gita.xaznabankingclone.presentation.tabs.HomeTab
import uz.mobiler.gita.xaznabankingclone.presentation.tabs.MonitoringTab
import uz.mobiler.gita.xaznabankingclone.presentation.tabs.PaymentsTab
import uz.mobiler.gita.xaznabankingclone.presentation.tabs.ServicesTab
import uz.mobiler.gita.xaznabankingclone.presentation.tabs.TransfersTab

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        TabNavigator(tab = HomeTab()) { _ ->
            Scaffold(
                containerColor = MaterialTheme.colorScheme.onBackground,
                bottomBar = {
                    Surface(
                        color = MaterialTheme.colorScheme.onBackground,
                        shadowElevation = 16.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceAround) {
                                TabNavigationItem(HomeTab())
                                TabNavigationItem(TransfersTab())
                                TabNavigationItem(PaymentsTab())
                                TabNavigationItem(MonitoringTab())
                                TabNavigationItem(ServicesTab())
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    CurrentTab()
                }
            }
        }
    }
}