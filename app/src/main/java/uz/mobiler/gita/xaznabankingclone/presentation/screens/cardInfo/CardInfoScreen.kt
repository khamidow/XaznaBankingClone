package uz.mobiler.gita.xaznabankingclone.presentation.screens.cardInfo

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.presenter.viewModels.cardInfoScreen.CardInfoContract
import uz.mobiler.gita.presenter.viewModels.cardInfoScreen.CardInfoViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.redColor
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import uz.mobiler.gita.xaznabankingclone.utils.formatUzs
import uz.mobiler.gita.xaznabankingclone.utils.toIconCard

class CardInfoScreen(private val cardData: CardData) : Screen {
    @Composable
    override fun Content() {
        val viewModel: CardInfoContract.ViewModel = hiltViewModel<CardInfoViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        viewModel.collectSideEffect {
            when (it) {
                is CardInfoContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is CardInfoContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }

                is CardInfoContract.SideEffect.PopBack -> {
                    navigator?.pop()
                }
            }
        }
        CardInfoContent(
            cardData,
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardInfoContent(
    cardData: CardData,
    uiState: CardInfoContract.UiState,
    onEventDispatcher: (CardInfoContract.Intent) -> Unit
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    val prefSettings = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    var showActionsSheet by remember { mutableStateOf(false) }
    val actionsSheetState = rememberModalBottomSheetState(true)
    var switchState by remember {
        mutableStateOf(prefSettings.getBoolean("monitoring_${cardData.id}", false))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkGradient)
            .safeDrawingPadding()
    ) {

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkGradient)
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
                    text = cardData.holderName,
                    color = white,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(18.dp)
                        .size(24.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_download),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val bcg = when (pref.getString("card_${cardData.id}", "").toString()) {
                "1" -> R.drawable.card_bcg_1
                "2" -> R.drawable.card_bcg_2
                "3" -> R.drawable.card_bcg_3
                "4" -> R.drawable.card_bcg_4
                else -> R.drawable.card_bcg_1
            }

            Box(
                Modifier
                    .padding(horizontal = 18.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(214.dp)
                    .clip(RoundedCornerShape(18.dp))
            ) {
                Image(
                    painter = painterResource(bcg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_xalqbanki_logo),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(white),
                        modifier = Modifier.height(32.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(cardData.holderName, color = white, fontSize = 14.sp)
                        Spacer(Modifier.weight(1f))
                        if (cardData.isMain) {
                            Image(
                                painter = painterResource(R.drawable.ic_star),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(white),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    if (cardData.isBlocked) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(redColor, RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_bloack),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(white),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                stringResource(R.string.expired_card),
                                color = white,
                                fontWeight = FontWeight.W600,
                                fontSize = 14.sp
                            )
                        }
                    } else {
                        Text(
                            cardData.balance.formatUzs(),
                            color = white,
                            fontWeight = FontWeight.W700,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 22.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            cardData.maskedNumber,
                            color = white,
                            fontWeight = FontWeight.W500,
                            fontSize = 17.sp,
                        )
                        Text(
                            cardData.expiry,
                            color = white,
                            fontWeight = FontWeight.W500,
                            fontSize = 17.sp, modifier = Modifier.padding(start = 18.dp)
                        )
                        Spacer(Modifier.weight(1f))
                        Image(
                            painter = painterResource(cardData.type.toIconCard()),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 18.dp)
                    .fillMaxWidth()
                    .height(64.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_get_money),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        stringResource(R.string.replenish),
                        color = lightWhite,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(Modifier.weight(1f))
                }
                VerticalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onPrimaryFixed)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_send_money),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        stringResource(R.string.send),
                        color = lightWhite,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(Modifier.weight(1f))
                }
                VerticalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onPrimaryFixed)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_qr_code),
                        contentDescription = null,
                        modifier = Modifier
                            .size(26.dp)
                            .padding(1.dp)
                    )
                    Text(
                        stringResource(R.string.qr_share),
                        color = lightWhite,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(Modifier.weight(1f))
                }
                VerticalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onPrimaryFixed)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            showActionsSheet = true
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_setting),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(enabled),
                        modifier = Modifier
                            .size(26.dp)
                            .padding(1.dp)
                    )
                    Text(
                        stringResource(R.string.actions),
                        color = lightWhite,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(Modifier.weight(1f))
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(horizontal = 18.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_monitoring),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(Modifier.width(10.dp))

                    Column() {
                        Text(
                            text = stringResource(R.string.monitoring_card),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.W600
                        )
                        Text(
                            stringResource(R.string.monitoring_card_description),
                            color = MaterialTheme.colorScheme.onPrimaryFixed,
                            fontSize = 13.sp,
                            lineHeight = 1.em
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Switch(
                        switchState,
                        onCheckedChange = {
                            switchState = !switchState
                            prefSettings.edit().putBoolean("monitoring_${cardData.id}", switchState)
                                .apply()
                        },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .scale(0.9f)
                            .height(10.dp)
                    )
                }
            }
        }
        if (showActionsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showActionsSheet = false },
                sheetState = actionsSheetState,
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                stringResource(R.string.select_action),
                                fontSize = 21.sp,
                                fontWeight = FontWeight.W600,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.weight(1f))
                            Image(
                                painter = painterResource(R.drawable.ic_close),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { showActionsSheet = false }
                            )
                        }
                        HorizontalDivider(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 18.dp),
                            color = MaterialTheme.colorScheme.onPrimaryFixed,
                            thickness = 1.dp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable{
                                    onEventDispatcher(CardInfoContract.Intent.OnSetMain(cardData.id))
                                    showActionsSheet = false
                                }
                                .padding(vertical = 16.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_star_outline),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = stringResource(R.string.make_main),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 15.sp
                            )
                        }
                        HorizontalDivider(
                            Modifier
                                .fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onPrimaryFixed,
                            thickness = 1.dp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable{
                                    onEventDispatcher(CardInfoContract.Intent.OnDeleteCard(cardData.id))
                                    showActionsSheet = false
                                }
                                .padding(vertical = 16.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_delete),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = stringResource(R.string.delete_card),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 15.sp
                            )
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
//private fun CardInfoContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
//        CardInfoContent(
//            CardData(
//                "",
//                "8809 **** **** 6152",
//                "NEW HOLDER",
//                "03/2028",
//                1200938,
//                "UZS",
//                false,
//                false,
//                "UZCARD"
//            )
//        )
//    }
//}