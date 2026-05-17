package uz.mobiler.gita.xaznabankingclone.presentation.screens.monitoring

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.core.models.FilterType
import uz.mobiler.gita.core.models.TransactionUiItem
import uz.mobiler.gita.presenter.viewModels.monitoringScreen.MonitoringContract
import uz.mobiler.gita.presenter.viewModels.monitoringScreen.MonitoringViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.redColor
import uz.mobiler.gita.xaznabankingclone.utils.formatUzs

class MonitoringScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: MonitoringContract.ViewModel = hiltViewModel<MonitoringViewModel>()
        val uiState = viewModel.collectAsState()
        val pagingItems =
            uiState.value.transactions?.collectAsLazyPagingItems()
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow.parent
        viewModel.collectSideEffect {
            when (it) {
                is MonitoringContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is MonitoringContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }

                is MonitoringContract.SideEffect.ScrollToTop -> {
                    // handled inside MonitoringContent via listState
                }
            }
        }
        MonitoringContent(
            pagingItems,
            uiState.value,
            viewModel::onEventDispatcher,
        )
    }
}

@Composable
private fun MonitoringContent(
    pagingItems: LazyPagingItems<TransactionUiItem>?,
    uiState: MonitoringContract.UiState,
    onEventDispatcher: (MonitoringContract.Intent) -> Unit,
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val snapshot = pagingItems?.itemSnapshotList?.items ?: emptyList()
    val income = snapshot
        .filterIsInstance<TransactionUiItem.Item>()
        .filter { it.item.type.contains("IN") }
        .sumOf { it.item.amount }

    val output = snapshot
        .filterIsInstance<TransactionUiItem.Item>()
        .filter { it.item.type.contains("OUT") }
        .sumOf { it.item.amount }

    var selectedFilter by remember { mutableStateOf(FilterType.NONE) }

    val isRefreshing = pagingItems?.loadState?.refresh is LoadState.Loading
    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) {
            listState.animateScrollToItem(0)
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            pagingItems?.refresh()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryFixedDim)
                .padding(top = 18.dp, start = 18.dp, end = 18.dp)
        ) {
            Text(
                stringResource(R.string.monitoring),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.W700,
            )
            Row(
                modifier = Modifier
                    .padding(top = 18.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .weight(1f)
                        .height(78.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = if (selectedFilter == FilterType.INCOME) 2.dp else 1.dp,
                            color = if (selectedFilter == FilterType.INCOME) enabled else MaterialTheme.colorScheme.onPrimaryFixed,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            selectedFilter = if (selectedFilter == FilterType.INCOME) {
                                onEventDispatcher(
                                    MonitoringContract.Intent.Refresh(cardId = "", type = "")
                                )
                                FilterType.NONE
                            } else {
                                onEventDispatcher(
                                    MonitoringContract.Intent.Refresh(
                                        cardId = "",
                                        type = "TRANSFER_IN"
                                    )
                                )
                                FilterType.INCOME
                            }
                        }
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.ic_income),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            stringResource(R.string.income),
                            color = MaterialTheme.colorScheme.onPrimaryFixed,
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                    Text(
                        income.formatUzs(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.W700,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .weight(1f)
                        .height(78.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = if (selectedFilter == FilterType.OUTPUT) 2.dp else 1.dp,
                            color = if (selectedFilter == FilterType.OUTPUT) redColor else MaterialTheme.colorScheme.onPrimaryFixed,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            selectedFilter = if (selectedFilter == FilterType.OUTPUT) {
                                onEventDispatcher(
                                    MonitoringContract.Intent.Refresh(cardId = "", type = "")
                                )
                                FilterType.NONE
                            } else {
                                onEventDispatcher(
                                    MonitoringContract.Intent.Refresh(
                                        cardId = "",
                                        type = "TRANSFER_OUT"
                                    )
                                )
                                FilterType.OUTPUT
                            }
                        }
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.ic_output),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            stringResource(R.string.output),
                            color = MaterialTheme.colorScheme.onPrimaryFixed,
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                    Text(
                        output.formatUzs(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.W700,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                pagingItems?.let { items ->
                    items(items.itemCount) { index ->
                        when (val item = items[index]) {
                            is TransactionUiItem.Header -> {
                                Text(
                                    text = item.date,
                                    color = MaterialTheme.colorScheme.onPrimaryFixed,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W600,
                                    modifier = Modifier.padding(top = 6.dp)
                                )
                            }

                            is TransactionUiItem.Item -> {
                                val isIn = item.item.type.contains("IN")
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                ) {
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.ic_uzcard_logo),
                                            contentDescription = null,
                                            modifier = Modifier.size(36.dp)
                                        )
                                        Column(
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                                .fillMaxHeight()
                                                .width(182.dp)
                                        ) {
                                            Spacer(Modifier.weight(1f))
                                            Text(
                                                item.item.cardId,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontSize = 16.sp,
                                                maxLines = 1,
                                                modifier = Modifier
                                                    .basicMarquee(
                                                        iterations = Int.MAX_VALUE,
                                                        initialDelayMillis = 3111,
                                                        velocity = 55.dp
                                                    )
                                            )
                                            Text(
                                                item.item.type,
                                                color = if (isIn) enabled else redColor,
                                                fontWeight = FontWeight.W600,
                                                fontSize = 14.sp,
                                                modifier = Modifier.padding(top = 2.dp)
                                            )
                                            Spacer(Modifier.weight(1f))
                                        }
                                        Spacer(Modifier.weight(1f))
                                        Column(
                                            modifier = Modifier.fillMaxHeight(),
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            Spacer(Modifier.weight(1f))
                                            Text(
                                                item.item.createdAt.substring(11, 16),
                                                color = MaterialTheme.colorScheme.onPrimaryFixed,
                                                fontSize = 14.sp
                                            )
                                            Text(
                                                (if (isIn) "+" else "-") + item.item.amount.formatUzs(),
                                                color = if (isIn) enabled else redColor,
                                                fontWeight = FontWeight.W500,
                                                fontSize = 14.sp,
                                                modifier = Modifier.padding(top = 2.dp)
                                            )
                                            Spacer(Modifier.weight(1f))
                                        }
                                    }
                                    HorizontalDivider(
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryFixed,
                                        modifier = Modifier.padding(bottom = 1.dp)
                                    )
                                }
                            }

                            null -> Unit
                        }
                    }
                }
            }
        }
    }
    if (uiState.loading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(loadingTransparentBcg)
        ) {
            val imageLoader = ImageLoader.Builder(context)
                .components { add(GifDecoder.Factory()) }
                .build()
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.gif_loading)
                    .build(),
                contentDescription = null,
                imageLoader = imageLoader,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(84.dp)
            )
        }
    }
}

//@Preview
//@Composable
//private fun MonitoringContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
//        MonitoringContent()
//    }
//}