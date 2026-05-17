package uz.mobiler.gita.xaznabankingclone.presentation.screens.betweenMyCards

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.presenter.viewModels.betweenMyCardsScreen.BetweenMyCardsContract
import uz.mobiler.gita.presenter.viewModels.betweenMyCardsScreen.BetweenMyCardsViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.firstPin.FirstPinScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import uz.mobiler.gita.xaznabankingclone.utils.formatUzs
import uz.mobiler.gita.xaznabankingclone.utils.toIconCard

class BetweenMyCardsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: BetweenMyCardsContract.ViewModel = hiltViewModel<BetweenMyCardsViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        viewModel.collectSideEffect {
            when (it) {
                is BetweenMyCardsContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is BetweenMyCardsContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }

                is BetweenMyCardsContract.SideEffect.PinScreen -> {
                    navigator?.push(FirstPinScreen(true))
                }

                is BetweenMyCardsContract.SideEffect.PopBack -> {
                    navigator?.pop()
                }
            }
        }
        BetweenMyCardsContent(
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BetweenMyCardsContent(
    uiState: BetweenMyCardsContract.UiState,
    onEventDispatcher: (BetweenMyCardsContract.Intent) -> Unit
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    val cardSheetState1 = rememberModalBottomSheetState(true)
    var showCardSheet1 by remember { mutableStateOf(false) }
    val cardSheetState2 = rememberModalBottomSheetState(true)
    var showCardSheet2 by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryFixedDim)
            .safeDrawingPadding()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryFixedDim)
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
                    text = stringResource(R.string.between_my_cards),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(28.dp))
            Text(
                stringResource(R.string.sender),
                color = MaterialTheme.colorScheme.onPrimaryFixed,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 18.dp)
            )
            Row(
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, top = 4.dp)
                    .fillMaxWidth()
                    .height(82.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryFixed,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        showCardSheet1 = true
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(uiState.fromCard!!.type.toIconCard()),
                    contentDescription = null,
                    modifier = Modifier.width(32.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .fillMaxHeight()
                ) {
                    Spacer(Modifier.weight(1f))
                    Text(
                        uiState.fromCard?.balance?.formatUzs().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.W600,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        uiState.fromCard?.maskedNumber.toString(),
                        color = MaterialTheme.colorScheme.onPrimaryFixed,
                        fontWeight = FontWeight.W500,
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_top),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed),
                        modifier = Modifier.size(22.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_bottom),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.weight(1f))
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.receiver),
                    color = MaterialTheme.colorScheme.onPrimaryFixed,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 18.dp)
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_replace),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primaryFixedDim),
                    modifier = Modifier
                        .padding(end = 18.dp)
                        .size(28.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .clickable {
                            onEventDispatcher(BetweenMyCardsContract.Intent.OnReplacePlaces)
                        }
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(6.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp)
                    .fillMaxWidth()
                    .height(82.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryFixed,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        showCardSheet2 = true
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(uiState.toCard!!.type.toIconCard()),
                    contentDescription = null,
                    modifier = Modifier.width(32.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .fillMaxHeight()
                ) {
                    Spacer(Modifier.weight(1f))
                    Text(
                        uiState.toCard?.balance?.formatUzs().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.W600,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        uiState.toCard?.maskedNumber.toString(),
                        color = MaterialTheme.colorScheme.onPrimaryFixed,
                        fontWeight = FontWeight.W500,
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_top),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed),
                        modifier = Modifier.size(22.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_bottom),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.weight(1f))
                }
            }

            Text(
                stringResource(R.string.transfer_amount),
                color = MaterialTheme.colorScheme.onPrimaryFixed,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 18.dp, top = 12.dp)
            )

            Row(
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, top = 6.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryFixed,
                        RoundedCornerShape(12.dp)
                    )
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = amount,
                    onValueChange = {
                        amount = it.filter { char -> char.isDigit() }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600
                    ),
                    decorationBox = { innerTextField ->
                        Box {
                            if (amount.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.zero_sum),
                                    color = MaterialTheme.colorScheme.onPrimaryFixed,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.W600
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
        Text(
            stringResource(R.string.next).uppercase(),
            color = if (amount.length >= 3) white else MaterialTheme.colorScheme.onPrimaryFixed,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(18.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(amount.length >= 3, onClick = {
                    onEventDispatcher(
                        BetweenMyCardsContract.Intent.OnTransfer(
                            uiState.fromCard?.id.toString(),
                            pref.getString(uiState.toCard?.id.toString(),"")?:"",
                            amount.toLong()
                        )
                    )
                })
                .background(
                    if (amount.length >= 3) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onBackground
                )
                .padding(11.dp)
        )

        if (showCardSheet1) {
            ModalBottomSheet(
                onDismissRequest = { showCardSheet1 = false },
                sheetState = cardSheetState1,
                dragHandle = null
            ) {
                CompositionLocalProvider(LocalContext provides context) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(18.dp)
                    ) {
                        Spacer(Modifier.height(2.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .clickable {
                                        showCardSheet1 = false
                                    }
                                    .background(MaterialTheme.colorScheme.onPrimary),
                                text = ""
                            )
                        }
                        Text(
                            stringResource(R.string.cards),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.W700,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 18.dp)
                        )
                        LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                            items(uiState.cards) {
                                if (it.id != uiState.toCard?.id.toString()) {
                                    Row(
                                        modifier = Modifier
                                            .padding(top = 6.dp)
                                            .fillMaxWidth()
                                            .height(78.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .border(
                                                1.dp,
                                                color = MaterialTheme.colorScheme.onPrimaryFixed,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .clickable {
                                                onEventDispatcher(
                                                    BetweenMyCardsContract.Intent.OnChangeCurrent1(
                                                        it
                                                    )
                                                )
                                                showCardSheet1 = false
                                            }
                                            .background(MaterialTheme.colorScheme.onBackground)
                                            .padding(horizontal = 20.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(it.type.toIconCard()),
                                            contentDescription = null,
                                            modifier = Modifier.width(32.dp)
                                        )
                                        Column(
                                            modifier = Modifier
                                                .padding(start = 14.dp)
                                                .fillMaxHeight()
                                        ) {
                                            Spacer(Modifier.weight(1f))
                                            Text(
                                                it.balance.formatUzs(),
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontWeight = FontWeight.W600,
                                                fontSize = 18.sp
                                            )
                                            Spacer(Modifier.height(4.dp))
                                            Text(
                                                it.maskedNumber,
                                                color = MaterialTheme.colorScheme.onPrimaryFixed,
                                                fontWeight = FontWeight.W500,
                                                fontSize = 15.sp
                                            )
                                            Spacer(Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                            item {
                                Spacer(Modifier.height(32.dp))
                            }
                        }
                    }
                }
            }
        }
        if (showCardSheet2) {
            ModalBottomSheet(
                onDismissRequest = { showCardSheet2 = false },
                sheetState = cardSheetState2,
                dragHandle = null
            ) {
                CompositionLocalProvider(LocalContext provides context) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(18.dp)
                    ) {
                        Spacer(Modifier.height(2.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .clickable {
                                        showCardSheet2 = false
                                    }
                                    .background(MaterialTheme.colorScheme.onPrimary),
                                text = ""
                            )
                        }
                        Text(
                            "UZCARD",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.W700,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 18.dp)
                        )
                        LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                            items(uiState.cards) {
                                if (it.id != uiState.fromCard?.id.toString()) {
                                    Row(
                                        modifier = Modifier
                                            .padding(top = 6.dp)
                                            .fillMaxWidth()
                                            .height(78.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .border(
                                                1.dp,
                                                color = MaterialTheme.colorScheme.onPrimaryFixed,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .clickable {
                                                onEventDispatcher(
                                                    BetweenMyCardsContract.Intent.OnChangeCurrent2(
                                                        it
                                                    )
                                                )
                                                showCardSheet2 = false
                                            }
                                            .background(MaterialTheme.colorScheme.onBackground)
                                            .padding(horizontal = 20.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(it.type.toIconCard()),
                                            contentDescription = null,
                                            modifier = Modifier.width(32.dp)
                                        )
                                        Column(
                                            modifier = Modifier
                                                .padding(start = 14.dp)
                                                .fillMaxHeight()
                                        ) {
                                            Spacer(Modifier.weight(1f))
                                            Text(
                                                it.balance.formatUzs(),
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontWeight = FontWeight.W600,
                                                fontSize = 18.sp
                                            )
                                            Spacer(Modifier.height(4.dp))
                                            Text(
                                                it.maskedNumber,
                                                color = MaterialTheme.colorScheme.onPrimaryFixed,
                                                fontWeight = FontWeight.W500,
                                                fontSize = 15.sp
                                            )
                                            Spacer(Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                            item {
                                Spacer(Modifier.height(32.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun BetweenMyCardsContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.DARK) {
//        BetweenMyCardsContent()
//    }
//}