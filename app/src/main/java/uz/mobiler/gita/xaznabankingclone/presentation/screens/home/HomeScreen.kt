package uz.mobiler.gita.xaznabankingclone.presentation.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.presenter.viewModels.homeScreen.HomeContract
import uz.mobiler.gita.presenter.viewModels.homeScreen.HomeViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.items.BannerCard
import uz.mobiler.gita.xaznabankingclone.presentation.screens.addCard.AddCardScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.profile.ProfileScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGray
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import uz.mobiler.gita.xaznabankingclone.ui.theme.yellow
import uz.mobiler.gita.xaznabankingclone.utils.formatUzs
import uz.mobiler.gita.xaznabankingclone.utils.toIconCard

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: HomeContract.ViewModel = hiltViewModel<HomeViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        viewModel.collectSideEffect {
            when (it) {
                is HomeContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is HomeContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }
            }
        }
        HomeContent(
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
private fun HomeContent(
    uiState: HomeContract.UiState,
    onEventDispatcher: (HomeContract.Intent) -> Unit
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow.parent
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var phoneNumber by remember { mutableStateOf("") }
    var showMoneyState by remember { mutableStateOf(false) }
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    val name = pref.getString("name", "") ?: ""
    var imageUri by remember { mutableStateOf(pref.getString("image_uri", null)) }
    val repeatedItems = remember(banners) { List(1000) { banners[it % banners.size] } }
    val startIndex = remember { 500 - (500 % banners.size) }

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val density = LocalDensity.current
    LaunchedEffect(Unit) {
        while (true) {
            delay(7000)
            val itemWidthPx = with(density) { (screenWidth - 32.dp + 12.dp).roundToPx() }
            listState.animateScrollBy(itemWidthPx.toFloat())
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryFixed)
                    .padding(vertical = 18.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                navigator?.push(ProfileScreen())
                            }
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_profile),
                        contentDescription = null,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                navigator?.push(ProfileScreen())
                            }
                    )
                }
                Text(
                    name.ifEmpty { stringResource(R.string.new_user) },
                    color = white,
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .clickable {
                            navigator?.push(ProfileScreen())
                        }
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_chat),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                        .size(24.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_bell),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.secondaryFixed,
                                MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    )
                    .padding(top = 8.dp)
            ) {
                Column() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .border(
                                    1.3.dp,
                                    lightWhite,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 4.dp, vertical = 6.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_top),
                                contentDescription = null,
                                modifier = Modifier.size(21.dp)
                            )
                            Image(
                                painter = painterResource(R.drawable.ic_bottom),
                                contentDescription = null,
                                modifier = Modifier.size(21.dp)
                            )
                        }

                        Column(modifier = Modifier.padding(start = 12.dp)) {
                            Row() {
                                Text(
                                    stringResource(R.string.total_balance),
                                    color = lightWhite,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.W500
                                )
                                Image(
                                    painter = painterResource(if (showMoneyState) R.drawable.ic_eye_open else R.drawable.ic_eye_close),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(white),
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .size(24.dp)
                                        .clip(RoundedCornerShape(50.dp))
                                        .clickable {
                                            showMoneyState = !showMoneyState
                                        }
                                )
                            }

                            Text(
                                if (showMoneyState) {
                                    if (uiState.mainCard == null) "-----"
                                    else uiState.mainCard?.balance?.formatUzs()!!
                                } else "•••••",
                                color = white,
                                fontWeight = FontWeight.W700,
                                fontSize = 28.sp
                            )

                        }
                    }
                    LazyRow(modifier = Modifier.padding(top = 28.dp)) {
                        item {
                            Spacer(Modifier.width(16.dp))
                        }
                        items(mainItems) {
                            Column(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .defaultMinSize(88.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        MaterialTheme.colorScheme.onSecondaryContainer,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(top = 8.dp, bottom = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Image(
                                    painter = painterResource(it.icon),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(white),
                                    modifier = Modifier.size(26.dp)
                                )
                                Text(
                                    it.name,
                                    color = white,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp)
                ) {
                    item { Spacer(modifier = Modifier.width(16.dp)) }
                    items(uiState.cards) {
                        Column(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .width(175.dp)
                                .height(125.dp)
                                .shadow(
                                    7.dp,
                                    RoundedCornerShape(12.dp),
                                    ambientColor = MaterialTheme.colorScheme.onPrimary
                                )
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(10.dp)
                        ) {
                            Row() {
                                Image(
                                    painter = painterResource(R.drawable.ic_xalqbanki_logo),
                                    contentDescription = null,
                                    modifier = Modifier.height(30.dp)
                                )
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = painterResource(it.type.toIconCard()),
                                    contentDescription = null,
                                    modifier = Modifier.height(18.dp)
                                )
                            }
                            if (it.isBlocked) {
                                Row(
                                    modifier = Modifier.padding(top = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_bloack),
                                        contentDescription = null,
                                        modifier = Modifier.size(26.dp)
                                    )
                                    Text(
                                        stringResource(R.string.expired_card),
                                        fontSize = 16.sp,
                                        maxLines = 2,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontWeight = FontWeight.W700,
                                        lineHeight = 1.em,
                                        modifier = Modifier.padding(start = 6.dp)
                                    )
                                }
                            } else {
                                Text(
                                    it.holderName,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                                Text(
                                    it.balance.formatUzs(),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W700,
                                    modifier = Modifier.padding(top = 0.dp)
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    it.maskedNumber,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontSize = 14.sp
                                )
                                Spacer(Modifier.weight(1f))
                                if (it.isMain) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_star_outline),
                                        contentDescription = null,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .width(175.dp)
                                .height(125.dp)
                                .shadow(
                                    7.dp,
                                    RoundedCornerShape(12.dp),
                                    ambientColor = MaterialTheme.colorScheme.onPrimary
                                )
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    navigator?.push(AddCardScreen())
                                }
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.weight(1f))
                            Image(
                                painter = painterResource(R.drawable.ic_add_outline_green),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                stringResource(R.string.add_card),
                                fontWeight = FontWeight.W600,
                                color = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Spacer(Modifier.width(16.dp))
                    }
                    items(cashbackItems) {
                        val color = if (it.color == "green") enabled else yellow
                        Row(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .width(screenWidth - 32.dp)
                                .height(90.dp)
                                .shadow(
                                    7.dp,
                                    RoundedCornerShape(12.dp),
                                    ambientColor = MaterialTheme.colorScheme.onPrimary
                                )
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(start = 8.dp, bottom = 8.dp, top = 8.dp)
                                .drawBehind {
                                    drawRoundRect(
                                        color = color,
                                        style = Stroke(
                                            width = 1.dp.toPx(),
                                            pathEffect = PathEffect.dashPathEffect(
                                                floatArrayOf(16f, 12f)
                                            )
                                        ),
                                        cornerRadius = CornerRadius(10.dp.toPx())
                                    )
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_caskback),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(color),
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(40.dp)
                            )
                            Column(modifier = Modifier.padding(start = 14.dp)) {
                                Spacer(Modifier.weight(1f))
                                Text(it.name, color = color, fontSize = 16.sp)
                                Text(
                                    text = if (it.isHidden) "*****" else it.value,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W700,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                                Spacer(Modifier.weight(1f))
                            }
                            Spacer(Modifier.weight(1f))
                            Image(
                                painter = painterResource(R.drawable.ic_cashback_end),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(color),
                                modifier = Modifier.height(38.dp)
                            )
                        }
                    }
                    item {
                        Spacer(Modifier.width(4.dp))
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .shadow(
                            5.dp,
                            RoundedCornerShape(12.dp),
                            ambientColor = MaterialTheme.colorScheme.onPrimary
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(12.dp)
                ) {
                    Text(
                        stringResource(R.string.pay_phone_number),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.W700,
                        fontSize = 18.sp
                    )
                    Row(
                        modifier = Modifier.padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "+998",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.W600,
                                color = MaterialTheme.colorScheme.onPrimary,
                                letterSpacing = 0.3.sp
                            )

                            VerticalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .height(28.dp)
                                    .padding(horizontal = 14.dp)
                            )

                            BasicTextField(
                                value = phoneNumber,
                                onValueChange = {
                                    if (it.length <= 9) {
                                        phoneNumber = it.filter { c -> c.isDigit() }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                textStyle = TextStyle(
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.W600,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                ),
                                cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary),
                                decorationBox = { innerTextField ->
                                    Box {
                                        if (phoneNumber.isEmpty()) {
                                            Text(
                                                text = "(__) ___-__-__",
                                                fontSize = 17.sp,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                letterSpacing = 0.5.sp
                                            )
                                        }
                                        innerTextField()
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Image(
                            painter = painterResource(R.drawable.ic_contact),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(10.dp)
                        )
                    }
                }

                LazyRow(
                    state = listState,
                    flingBehavior = snapBehavior,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 12.dp)
                        .fillMaxWidth()
                ) {
                    item { Spacer(modifier = Modifier.width(12.dp)) }
                    items(repeatedItems.size) { index ->
                        BannerCard(item = repeatedItems[index])
                    }
                    item { Spacer(modifier = Modifier.width(12.dp)) }
                }

                Text(
                    stringResource(R.string.hits),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W600,
                    fontSize = 20.sp, modifier = Modifier.padding(start = 16.dp)
                )

                LazyRow(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {
                    item { Spacer(Modifier.width(16.dp)) }
                    items(hintItems) {
                        Image(
                            painter = painterResource(it.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(64.dp)
                                .clip(
                                    RoundedCornerShape(12.dp)
                                )
                        )
                    }
                    item { Spacer(Modifier.width(16.dp)) }
                }

                Row(modifier = Modifier.padding(top = 14.dp, start = 16.dp, end = 16.dp)) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f)
                            .aspectRatio(1f)
                            .shadow(5.dp, RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_loans),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                stringResource(R.string.loans),
                                color = white,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W600,
                            )
                            Text(
                                stringResource(R.string.loans_description),
                                color = lightGray,
                                fontSize = 14.sp,
                                lineHeight = 1.em,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f)
                            .aspectRatio(1f)
                            .shadow(5.dp, RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_deposit),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                stringResource(R.string.deposits),
                                color = white,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W600,
                            )
                            Text(
                                stringResource(R.string.deposits_description),
                                color = lightGray,
                                fontSize = 14.sp,
                                lineHeight = 1.em,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .shadow(7.dp, RoundedCornerShape(14.dp))
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(14.dp)
                        )
                        .padding(14.dp)
                ) {

                    Text(
                        stringResource(R.string.exchange_rates),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    uiState.exchangeRates.forEach { item ->

                        val icon = when (item.currency) {
                            "USD" -> R.drawable.ic_usa
                            "EUR" -> R.drawable.ic_euro
                            "RUB" -> R.drawable.ic_rus
                            "GBP" -> R.drawable.ic_eng
                            "CNY" -> R.drawable.ic_china
                            else -> R.drawable.ic_uzb
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                painter = painterResource(icon),
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .size(28.dp)
                            )

                            Text(
                                item.currency,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.W600,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                            Spacer(Modifier.weight(1f))
                            Text(
                                text = item.buy.toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.W600,
                                fontSize = 18.sp,
                            )
                            Spacer(Modifier.weight(1f))
                            Text(
                                text = item.sell.toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.W600,
                                fontSize = 18.sp,
                            )
                            Spacer(Modifier.weight(1f))
                        }

                        HorizontalDivider(
                            thickness = 0.5.dp,
                            color = MaterialTheme.colorScheme.onPrimaryFixed,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.all_exchange_rates),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                        Image(
                            painter = painterResource(R.drawable.ic_next),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
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

private data class MainItemsData(
    val icon: Int,
    val name: String
)

private data class CashbackItemsData(
    val icon: Int,
    val color: String,
    val name: String,
    val value: String,
    val isHidden: Boolean
)

data class BannerItem(
    val title: String,
    val subtitle: String,
    val imageRes: Int
)

data class HintItem(
    val image: Int
)

private val mainItems = mutableListOf<MainItemsData>().apply {
    add(MainItemsData(R.drawable.ic_scan, "XaznaPay"))
    add(MainItemsData(R.drawable.ic_barcode, "XaznaPass"))
    add(MainItemsData(R.drawable.ic_alipay, "Alipay+"))
    add(MainItemsData(R.drawable.ic_humopay, "HumoPay"))
    add(MainItemsData(R.drawable.ic_home, "My Home"))
    add(MainItemsData(R.drawable.ic_bank, "My Bank"))
}

private val cashbackItems = mutableListOf<CashbackItemsData>().apply {
    add(CashbackItemsData(R.drawable.ic_caskback, "green", "CASHBACK", "6 426.25 UZS", false))
    add(CashbackItemsData(R.drawable.ic_caskback, "yellow", "REGULAR", "3 115.75 UZS", true))
}

val banners = listOf(
    BannerItem(
        "Repay the loan",
        "Repay loans from Xalq Bank without commission (0%)",
        R.drawable.ic_replay_the_loan
    ),
    BannerItem("Elcard", "Transfers to Kyrgyzstan's Elcard cards", R.drawable.ic_elcard),
    BannerItem(
        "Withdraw APS funds",
        "Now you don't need to visit the bank to recieve funds!",
        R.drawable.ic_aps_funds
    ),
    BannerItem(
        "Car insurance",
        "Insure your car without documents",
        R.drawable.ic_car_insurance
    ),
)

val hintItems = mutableListOf<HintItem>().apply {
    add(HintItem(R.drawable.ic_hint_cart))
    add(HintItem(R.drawable.ic_hint_steering_wheel))
    add(HintItem(R.drawable.ic_hint_home))
    add(HintItem(R.drawable.ic_hint_feet))
    add(HintItem(R.drawable.ic_hint_shield))
}

//@Preview
//@Composable
//private fun HomeContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
//        HomeContent()
//    }
//}
