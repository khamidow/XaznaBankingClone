package uz.mobiler.gita.xaznabankingclone.presentation.screens.transfers

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.presenter.viewModels.transfers.TransfersContract
import uz.mobiler.gita.presenter.viewModels.transfers.TransfersViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.items.KycDialog
import uz.mobiler.gita.xaznabankingclone.presentation.screens.askForMoney.AskForMoneyScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.betweenMyCards.BetweenMyCardsScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.identification.IdentificationScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.transferByRequisites.TransferByRequisitesScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.transferToCard.TransferToCardScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.transferToKyrgyzstan.TransferToKyrgyzstanScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.transferToWallet.TransferToWalletScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.universalPayment.UniversalPaymentScreen

class TransfersScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: TransfersContract.ViewModel = hiltViewModel<TransfersViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow.parent
        val kycSheetState = rememberModalBottomSheetState(true)
        var showKycSheet by remember { mutableStateOf(false) }
        viewModel.collectSideEffect {
            when (it) {
                is TransfersContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is TransfersContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }

                is TransfersContract.SideEffect.KycDialog -> {
                    showKycSheet = true
                }

                is TransfersContract.SideEffect.Navigate -> {
                    navigator?.push(it.screen)
                }
            }
        }
        TransfersContent(
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
private fun TransfersContent(
    uiState: TransfersContract.UiState,
    onEventDispatcher: (TransfersContract.Intent) -> Unit
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow.parent
    val doNotWork = stringResource(R.string.do_not_work)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryFixedDim)
            .padding(18.dp)
    ) {
        Text(
            stringResource(R.string.transfers),
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
                    .padding(end = 8.dp)
                    .weight(1f)
                    .height(108.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onEventDispatcher(TransfersContract.Intent.OnCheckKcy(TransferToCardScreen()))
//                        navigator?.push(TransferToCardScreen())
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(14.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_transfers),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    stringResource(R.string.transfer_to_card),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 1.1.em,
                    minLines = 2
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
                    .height(108.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onEventDispatcher(TransfersContract.Intent.OnCheckKcy(BetweenMyCardsScreen()))
//                        navigator?.push(BetweenMyCardsScreen())
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(14.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_cards),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    stringResource(R.string.between_my_cards),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 1.1.em,
                    minLines = 2
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
                    .height(108.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onEventDispatcher(TransfersContract.Intent.OnCheckKcy(TransferToWalletScreen()))
//                        navigator?.push(TransferToWalletScreen())
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(14.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_wallet),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    stringResource(R.string.transfer_to_wallet),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 1.1.em,
                    minLines = 2
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
                    .height(108.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onEventDispatcher(
                            TransfersContract.Intent.OnCheckKcy(
                                TransferByRequisitesScreen()
                            )
                        )
//                        navigator?.push(TransferByRequisitesScreen())
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(14.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_requisites),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    stringResource(R.string.transfer_by_requisites),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 1.1.em,
                    minLines = 2
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
                    .height(108.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        Toast.makeText(context, doNotWork, Toast.LENGTH_SHORT).show()
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(14.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_convert),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    stringResource(R.string.conversion),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 1.1.em,
                    minLines = 2
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
                    .height(108.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onEventDispatcher(TransfersContract.Intent.OnCheckKcy(UniversalPaymentScreen()))
//                        navigator?.push(UniversalPaymentScreen())
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(14.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_global),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    stringResource(R.string.international_transfers),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 1.1.em,
                    minLines = 2
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
                    .height(108.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onEventDispatcher(TransfersContract.Intent.OnCheckKcy(AskForMoneyScreen()))
//                        navigator?.push(AskForMoneyScreen())
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(14.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_get_money),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    stringResource(R.string.request_for_funds),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 1.1.em,
                    minLines = 2
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
                    .height(108.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onEventDispatcher(
                            TransfersContract.Intent.OnCheckKcy(
                                TransferToKyrgyzstanScreen()
                            )
                        )
//                        navigator?.push(TransferToKyrgyzstanScreen())
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(14.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_elcard_logo),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    stringResource(R.string.money_transfer_to_kyrgyzstan),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 1.1.em,
                    minLines = 2
                )
            }
        }
    }
}

//@Preview
//@Composable
//private fun TransfersContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.DARK) {
//        TransfersContent()
//    }
//}