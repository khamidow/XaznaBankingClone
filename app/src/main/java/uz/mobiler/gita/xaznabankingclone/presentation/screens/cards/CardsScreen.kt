package uz.mobiler.gita.xaznabankingclone.presentation.screens.cards

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import uz.mobiler.gita.presenter.viewModels.cardsScreen.CardsContract
import uz.mobiler.gita.presenter.viewModels.cardsScreen.CardsViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.addCard.AddCardScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.cardInfo.CardInfoScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.redColor
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import uz.mobiler.gita.xaznabankingclone.utils.formatUzs
import uz.mobiler.gita.xaznabankingclone.utils.toIconCard

class CardsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: CardsContract.ViewModel = hiltViewModel<CardsViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        viewModel.collectSideEffect {
            when (it) {
                is CardsContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is CardsContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }
            }
        }
        CardsContent(
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun CardsContent(
    uiState: CardsContract.UiState,
    onEventDispatcher: (CardsContract.Intent) -> Unit
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
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
                    text = stringResource(R.string.all_cards),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(uiState.cards) {
                    val bcg = when (pref.getString("card_${it.id}", "").toString()) {
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
                            .clickable{
                                navigator?.push(CardInfoScreen(it))
                            }
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
                                Text(it.holderName, color = white, fontSize = 14.sp)
                                Spacer(Modifier.weight(1f))
                                if (it.isMain) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_star),
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(white),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            if (it.isBlocked) {
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
                                    it.balance.formatUzs(),
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
                                    it.maskedNumber,
                                    color = white,
                                    fontWeight = FontWeight.W500,
                                    fontSize = 17.sp,
                                )
                                Text(
                                    it.expiry,
                                    color = white,
                                    fontWeight = FontWeight.W500,
                                    fontSize = 17.sp, modifier = Modifier.padding(start = 18.dp)
                                )
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = painterResource(it.type.toIconCard()),
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
            }

        }
        FloatingActionButton(
            onClick = {
                navigator?.push(AddCardScreen())
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(22.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(100.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_add_thin),
                contentDescription = null,
                modifier = Modifier.size(32.dp).padding(6.dp)
            )
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
//private fun CardsContentPreview() {
//    CardsContent()
//}