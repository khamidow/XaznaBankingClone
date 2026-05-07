package uz.mobiler.gita.xaznabankingclone.presentation.tabNavigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGray
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val color = if (tabNavigator.current == tab) enabled else lightWhite
    NavigationBarItem(
        colors = NavigationBarItemColors(
            selectedIconColor = enabled,
            selectedTextColor = enabled,
            selectedIndicatorColor = MaterialTheme.colorScheme.onBackground,
            unselectedIconColor = lightWhite,
            unselectedTextColor = lightWhite,
            disabledIconColor = lightGray,
            disabledTextColor = lightGray
        ),
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = tab.options.title,
                    tint = color,
                    modifier = Modifier.size(21.dp)
                )
            }
        },
        label = { Text(tab.options.title, color = color) }
    )
}
