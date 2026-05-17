package uz.mobiler.gita.xaznabankingclone.presentation.screens.payments

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.presenter.viewModels.paymentsScreen.PaymentsContract
import uz.mobiler.gita.presenter.viewModels.paymentsScreen.PaymentsViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.items.KycDialog
import uz.mobiler.gita.xaznabankingclone.presentation.screens.identification.IdentificationScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.paying.PayingScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import uz.mobiler.gita.xaznabankingclone.utils.toImageCategory
import uz.mobiler.gita.xaznabankingclone.utils.toImagePayment

class PaymentsScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: PaymentsContract.ViewModel = hiltViewModel<PaymentsViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow.parent
        val kycSheetState = rememberModalBottomSheetState(true)
        var showKycSheet by remember { mutableStateOf(false) }
        viewModel.collectSideEffect {
            when (it) {
                is PaymentsContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is PaymentsContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }

                is PaymentsContract.SideEffect.KycDialog -> {
                    showKycSheet = true
                }

                is PaymentsContract.SideEffect.Navigate -> {
                    navigator?.push(it.screen)
                }
            }
        }
        PaymentsContent(
            uiState.value,
            viewModel::onEventDispatcher
        )
        if (showKycSheet) {
            val localizedContext = LocalContext.current

            ModalBottomSheet(
                onDismissRequest = { showKycSheet = false },
                sheetState = kycSheetState,
                dragHandle = null
            ) {
                CompositionLocalProvider(LocalContext provides localizedContext) {
                    KycDialog(
                        onClick = {
                            showKycSheet = false
                            navigator?.push(IdentificationScreen())
                        },
                        onDismiss = {
                            showKycSheet = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun PaymentsContent(
    uiState: PaymentsContract.UiState,
    onEventDispatcher: (PaymentsContract.Intent) -> Unit
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow.parent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.secondaryFixed,
                            MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                )
                .padding(top = 18.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                ) {
                    Text(
                        stringResource(R.string.payment),
                        color = white,
                        fontWeight = FontWeight.W700,
                        fontSize = 26.sp
                    )
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_qr_code_complex),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .size(26.dp)
                    )
                }

                LazyRow(
                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 10.dp)
                        .fillMaxWidth()
                ) {
                    item { Spacer(modifier = Modifier.width(12.dp)) }
                    items(uiState.categories) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 1.dp)
                                .width(72.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    onEventDispatcher(PaymentsContract.Intent.OnSelectCategory(it))
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(it.toImageCategory()),
                                contentDescription = null,
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(white),
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (uiState.currentCategory != it) MaterialTheme.colorScheme.secondaryFixed
                                        else MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(16.dp)
                            )
                            Text(
                                it,
                                maxLines = 1,
                                color = white,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                    item { Spacer(Modifier.width(12.dp)) }
                }
            }
        }

        FlowRow(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxItemsInEachRow = 3
        ) {

            uiState.paymentProviders.forEach {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            onEventDispatcher(PaymentsContract.Intent.OnCheckKcy(PayingScreen(it)))
//                            navigator?.push(PayingScreen(it))
                        }
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(it.id.toImagePayment()),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(72.dp)
                    )
                    Spacer(Modifier.weight(1f))

//                    Text(
//                        it.name,
//                        color = MaterialTheme.colorScheme.onPrimary,
//                        fontWeight = FontWeight.W500,
//                        fontSize = 14.sp,
//                        minLines = 2,
//                        lineHeight = 1.em,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier
//                            .padding(top = 4.dp, start = 6.dp, end = 6.dp)
//                            .fillMaxWidth()
//                    )
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
//private fun PaymentsContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
//        PaymentsContent()
//    }
//}