package uz.mobiler.gita.xaznabankingclone.presentation.screens.widgetSettings

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme

class WidgetScreen : Screen {
    @Composable
    override fun Content() {
        WidgetScreenContent()
    }
}

@Composable
private fun WidgetScreenContent() {
    val navigator = LocalNavigator.current
    val allServices = stringResource(R.string.all_services)
    val myCards = stringResource(R.string.my_cards)
    val paymentOnSite = stringResource(R.string.payment_on_site)
    val exchangeRates = stringResource(R.string.exchange_rates)
    val fixedItems = remember {
        mutableStateListOf(
            WidgetItem(1, allServices),
            WidgetItem(2, myCards)
        )
    }

    val addedItems = remember {
        mutableStateListOf(
            WidgetItem(3, paymentOnSite),
            WidgetItem(4, exchangeRates)
        )
    }
    val availableItems = remember { mutableStateListOf<WidgetItem>() }

    val listState = rememberLazyListState()
    val fixedDrag = remember { DragDropState(listState, fixedItems, "fixed_") }
    val addedDrag = remember { DragDropState(listState, addedItems, "added_") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .safeDrawingPadding()
    ) {

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Box(
                    modifier = Modifier
                        .padding(18.dp)
                        .size(28.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimaryFixed,
                            RoundedCornerShape(6.dp)
                        )
                        .clickable {
                            navigator?.pop()
                        }
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed)
                    )
                }

                Text(
                    text = stringResource(R.string.notification),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(28.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {

                item {
                    Text(
                        text = stringResource(R.string.fixed_position),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                itemsIndexed(fixedItems, key = { _, it -> "fixed_${it.id}" }) { index, item ->
                    val dragging = fixedDrag.draggingIndex == index
                    WidgetRow(
                        item = item,
                        isDragging = dragging,
                        dragOffset = if (dragging) fixedDrag.dragOffset else 0f,
                        showHandle = true,
                        trailingIcon = null,
                        onDragStart = { off -> fixedDrag.onDragStart(off, index) },
                        onDrag = { dy -> fixedDrag.onDrag(dy) },
                        onDragEnd = { fixedDrag.onDragEnd() }
                    )
                }

                item { Spacer(Modifier.height(24.dp)) }

                item {
                    Text(
                        text = stringResource(R.string.added_widgets),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                itemsIndexed(addedItems, key = { _, it -> "added_${it.id}" }) { index, item ->
                    val dragging = addedDrag.draggingIndex == index
                    WidgetRow(
                        item = item,
                        isDragging = dragging,
                        dragOffset = if (dragging) addedDrag.dragOffset else 0f,
                        showHandle = true,
                        trailingIcon = R.drawable.ic_remove,
                        onTrailingClick = { addedItems.remove(item); availableItems.add(item) },
                        onDragStart = { off -> addedDrag.onDragStart(off, index) },
                        onDrag = { dy -> addedDrag.onDrag(dy) },
                        onDragEnd = { addedDrag.onDragEnd() }
                    )
                }

                item { Spacer(Modifier.height(24.dp)) }

                item {
                    Text(
                        text = stringResource(R.string.available_widgets),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                if (availableItems.isEmpty()) {
                    item { Spacer(Modifier.height(64.dp)) }
                } else {
                    itemsIndexed(
                        availableItems,
                        key = { _, it -> "avail_${it.id}" }) { index, item ->
                        WidgetRow(
                            item = item,
                            isDragging = false,
                            dragOffset = 0f,
                            showHandle = false,
                            trailingIcon = R.drawable.ic_add,
                            onTrailingClick = { availableItems.remove(item); addedItems.add(item) },
                            onDragStart = {},
                            onDrag = {},
                            onDragEnd = {}
                        )
                    }
                }

                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}

@Preview
@Composable
private fun WidgetScreenContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        WidgetScreenContent()
    }
}

data class WidgetItem(val id: Int, val label: String)

@Composable
fun WidgetRow(
    item: WidgetItem,
    isDragging: Boolean,
    dragOffset: Float,
    showHandle: Boolean,
    trailingIcon: Int?,
    onTrailingClick: (() -> Unit)? = null,
    onDragStart: (Offset) -> Unit,
    onDrag: (Float) -> Unit,
    onDragEnd: () -> Unit,
) {
    val elevation by animateDpAsState(if (isDragging) 8.dp else 1.dp, label = "elev")

    Box(
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth()
            .zIndex(if (isDragging) 1f else 0f)
            .graphicsLayer { translationY = dragOffset }
            .shadow(elevation, RoundedCornerShape(10.dp), clip = false)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isDragging) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background)
            .height(56.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
        ) {
            if (showHandle) {
                Box(
                    modifier = Modifier
                        .size(width = 28.dp, height = 48.dp)
                        .pointerInput(Unit) {
                            detectDragGesturesAfterLongPress(
                                onDragStart = { offset -> onDragStart(offset) },
                                onDrag = { change, amount ->
                                    change.consume()
                                    onDrag(amount.y)
                                },
                                onDragEnd = { onDragEnd() },
                                onDragCancel = { onDragEnd() }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    DragHandle()
                }
                Spacer(Modifier.width(10.dp))
            }

            Text(
                text = item.label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1f)
            )

            if (trailingIcon != null) {
                IconButton(
                    onClick = { onTrailingClick?.invoke() },
                    modifier = Modifier.size(36.dp)
                ) {
                    Image(
                        painter = painterResource(trailingIcon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DragHandle() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(20.dp)
    ) {
        repeat(3) {
            Box(
                Modifier
                    .fillMaxWidth(0.85f)
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.onPrimaryFixed, RoundedCornerShape(1.dp))
            )
        }
    }
}

class DragDropState(
    val listState: LazyListState,
    val items: MutableList<WidgetItem>,
    val keyPrefix: String
) {
    var draggingIndex by mutableStateOf<Int?>(null)
    var dragOffset by mutableFloatStateOf(0f)
    private var pointerY = 0f

    fun onDragStart(localOffset: Offset, itemIndex: Int) {
        draggingIndex = itemIndex
        dragOffset = 0f
        val info = listState.layoutInfo.visibleItemsInfo
            .firstOrNull { it.key == "${keyPrefix}${items[itemIndex].id}" }
        pointerY = (info?.offset ?: 0).toFloat() + localOffset.y
    }

    fun onDrag(delta: Float) {
        if (draggingIndex == null) return
        dragOffset += delta
        pointerY += delta
    }

    fun onDragEnd() {
        val idx = draggingIndex ?: run { dragOffset = 0f; return }

        val targetInfo = listState.layoutInfo.visibleItemsInfo
            .filter { it.key.toString().startsWith(keyPrefix) }
            .firstOrNull { info ->
                pointerY.toInt() in info.offset..(info.offset + info.size)
            }

        if (targetInfo != null) {
            val targetIdx = items.indexOfFirst {
                "${keyPrefix}${it.id}" == targetInfo.key
            }
            if (targetIdx >= 0 && targetIdx != idx) {
                items.add(targetIdx, items.removeAt(idx))
            }
        }

        draggingIndex = null
        dragOffset = 0f
    }
}