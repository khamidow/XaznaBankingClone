package uz.mobiler.gita.xaznabankingclone.presentation.screens.addCard

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
import uz.mobiler.gita.presenter.viewModels.addCardScreen.AddCardContract
import uz.mobiler.gita.presenter.viewModels.addCardScreen.AddCardViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGray
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import uz.mobiler.gita.xaznabankingclone.utils.CardNumberVisualTransformation

class AddCardScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: AddCardContract.ViewModel = hiltViewModel<AddCardViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        viewModel.collectSideEffect {
            when (it) {
                is AddCardContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is AddCardContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }
            }
        }
        AddCardContent(
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun AddCardContent(
    uiState: AddCardContract.UiState,
    onEventDispatcher: (AddCardContract.Intent) -> Unit
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    var cardNumber by remember {
        mutableStateOf("")
    }
    var expiryDate by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var cardBackground by remember {
        mutableStateOf(1)
    }
    var name by remember {
        mutableStateOf("")
    }
    var cardImage = when (cardBackground) {
        1 -> R.drawable.card_bcg_1
        2 -> R.drawable.card_bcg_2
        3 -> R.drawable.card_bcg_3
        4 -> R.drawable.card_bcg_4
        else -> R.drawable.card_bcg_1
    }
    val isAllFilled =
        cardNumber.length == 16 &&
                expiryDate.text.length == 5 &&
                name.isNotBlank()


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
                    text = stringResource(R.string.add_card),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(196.dp)
                    .clip(RoundedCornerShape(18.dp))
            ) {
                Image(
                    painter = painterResource(cardImage), contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 18.dp)) {
                    Text(
                        stringResource(R.string.card_number),
                        color = white,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .fillMaxWidth()
                            .background(
                                color = Color(0x26FAFAFA),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(1.dp, lightGray, RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = cardNumber,
                            onValueChange = { input ->
                                cardNumber = input.filter { ch -> ch.isDigit() }
                                    .take(16)
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            textStyle = TextStyle(
                                color = white,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.W600
                            ),
                            singleLine = true,
                            visualTransformation = CardNumberVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth(),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (cardNumber.isEmpty()) {
                                        Text(
                                            "0000 0000 0000 0000",
                                            color = lightWhite,
                                            fontSize = 17.sp
                                        )
                                    }

                                    innerTextField()
                                }
                            }
                        )
                    }

                    Text(
                        stringResource(R.string.expire_date),
                        color = white,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .width(100.dp)
                            .background(
                                color = Color(0x26FAFAFA),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(1.dp, lightGray, RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = expiryDate,
                            onValueChange = { input ->

                                val digits = input.text
                                    .filter { it.isDigit() }
                                    .take(4)

                                val formatted = buildString {
                                    digits.forEachIndexed { index, c ->
                                        append(c)

                                        if (index == 1 && index != digits.lastIndex) {
                                            append("/")
                                        }
                                    }
                                }

                                expiryDate = TextFieldValue(
                                    text = formatted,
                                    selection = TextRange(formatted.length)
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            textStyle = TextStyle(
                                color = white,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.W600
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .width(100.dp),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (expiryDate.text.isEmpty()) {
                                        Text("MM/YY", color = lightWhite, fontSize = 17.sp)
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            cardBackground = 1
                        }
                        .border(
                            if (cardBackground == 1) 1.5.dp else 0.dp,
                            color = enabled,
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.card_bcg_1),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (cardBackground == 1) {
                        Image(
                            painter = painterResource(R.drawable.ic_tick),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(28.dp)
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            cardBackground = 2
                        }
                        .border(
                            if (cardBackground == 2) 1.5.dp else 0.dp,
                            color = enabled,
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.card_bcg_2),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (cardBackground == 2) {
                        Image(
                            painter = painterResource(R.drawable.ic_tick),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(28.dp)
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            cardBackground = 3
                        }
                        .border(
                            if (cardBackground == 3) 1.5.dp else 0.dp,
                            color = enabled,
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.card_bcg_3),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (cardBackground == 3) {
                        Image(
                            painter = painterResource(R.drawable.ic_tick),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(28.dp)
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            cardBackground = 4
                        }
                        .border(
                            if (cardBackground == 4) 1.5.dp else 0.dp,
                            color = enabled,
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.card_bcg_4),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (cardBackground == 4) {
                        Image(
                            painter = painterResource(R.drawable.ic_tick),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(28.dp)
                        )
                    }
                }
            }

            Text(
                stringResource(R.string.card_name),
                fontWeight = FontWeight.W600,
                color = MaterialTheme.colorScheme.onPrimaryFixed,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )

            BasicTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = white,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W600
                ),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onPrimaryFixed,
                                RoundedCornerShape(12.dp)
                            )
                            .background(
                                MaterialTheme.colorScheme.tertiaryFixedDim,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        if (name.isEmpty()) {
                            Text(
                                text = "Enter name",
                                color = lightWhite,
                                fontSize = 17.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
        Text(
            stringResource(R.string.next).uppercase(),
            color = if (isAllFilled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(isAllFilled, onClick = {
                    onEventDispatcher(AddCardContract.Intent.OnAddCard(cardNumber.replace(" ", "")))
                })
                .background(if (isAllFilled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
                .padding(11.dp)
        )
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
//private fun AddCardContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
//        AddCardContent()
//    }
//}