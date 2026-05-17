package uz.mobiler.gita.xaznabankingclone.presentation.screens.loans

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
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
import uz.mobiler.gita.presenter.viewModels.loans.LoansContract
import uz.mobiler.gita.presenter.viewModels.loans.LoansViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.presentation.screens.addCard.AddCardScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.addLoan.AddLoanScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.repayLoanScreen.RepayLoanScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.mainColor
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import uz.mobiler.gita.xaznabankingclone.utils.formatUzs
import uz.mobiler.gita.xaznabankingclone.utils.toFormattedDate

class LoansScreen : Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel: LoansContract.ViewModel = hiltViewModel<LoansViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        viewModel.collectSideEffect {
            when (it) {
                is LoansContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    if (it.message == "Card is added") {
                        navigator?.pop()
                    }
                }

                is LoansContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }
            }
        }
        LoansContent(
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun LoansContent(
    uiState: LoansContract.UiState,
    onEventDispatcher: (LoansContract.Intent) -> Unit
) {
    val navigator = LocalNavigator.current
    val context = LocalContext.current
    val displayedLoans = if (uiState.isActiveTab) uiState.activeLoans else uiState.inactiveLoans

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
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
                    text = stringResource(R.string.loans),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .padding(4.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onEventDispatcher(LoansContract.Intent.OnTabSelected(true)) }
                            .background(
                                if (uiState.isActiveTab) MaterialTheme.colorScheme.tertiaryContainer
                                else MaterialTheme.colorScheme.onBackground
                            )
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(Modifier.weight(1f))
                        Text(
                            stringResource(R.string.active),
                            color = if (uiState.isActiveTab) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryFixed,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        Spacer(Modifier.weight(1f))
                    }
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onEventDispatcher(LoansContract.Intent.OnTabSelected(false)) }
                            .background(
                                if (!uiState.isActiveTab) MaterialTheme.colorScheme.tertiaryContainer
                                else MaterialTheme.colorScheme.onBackground
                            )
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(Modifier.weight(1f))
                        Text(
                            stringResource(R.string.inactive),
                            color = if (!uiState.isActiveTab) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryFixed,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        Spacer(Modifier.weight(1f))
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(top = 18.dp)
                    .fillMaxWidth()
            ) {
                items(displayedLoans) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 18.dp, vertical = 4.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable{
                                navigator?.push(RepayLoanScreen(it))
                            }
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(mainColor, lightGradient)
                                )
                            )
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(1f)
                        ) {
                            Text(
                                it.id,
                                color = white,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                maxLines = 1,
                                modifier = Modifier
                                    .basicMarquee(
                                        iterations = Int.MAX_VALUE,
                                        initialDelayMillis = 3111,
                                        velocity = 55.dp
                                    )
                            )
                            Text(
                                it.totalAmount.toLong().formatUzs(),
                                color = white,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(top = 3.dp)
                            )
                            Text(
                                it.nextDueDate.toFormattedDate(),
                                color = white,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 3.dp)
                            )
                        }
                        Image(
                            painter = painterResource(R.drawable.ic_loan),
                            contentDescription = null,
                            modifier = Modifier.size(68.dp)
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                navigator?.push(AddLoanScreen())
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
                modifier = Modifier
                    .size(32.dp)
                    .padding(6.dp)
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