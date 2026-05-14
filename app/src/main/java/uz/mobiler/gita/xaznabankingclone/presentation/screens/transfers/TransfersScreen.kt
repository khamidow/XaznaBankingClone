package uz.mobiler.gita.xaznabankingclone.presentation.screens.transfers

import android.widget.Space
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.presentation.screens.askForMoney.AskForMoneyScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.betweenMyCards.BetweenMyCardsScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.transferByRequisites.TransferByRequisitesScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.transferToCard.TransferToCardScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.transferToKyrgyzstan.TransferToKyrgyzstanScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.transferToWallet.TransferToWalletScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.universalPayment.UniversalPaymentScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme

class TransfersScreen : Screen {
    @Composable
    override fun Content() {
        TransfersContent()
    }
}

@Composable
private fun TransfersContent() {
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
                        navigator?.push(TransferToCardScreen())
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
                        navigator?.push(BetweenMyCardsScreen())
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
                        navigator?.push(TransferToWalletScreen())
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
                        navigator?.push(TransferByRequisitesScreen())
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
                    .clickable{
                        navigator?.push(UniversalPaymentScreen())
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
                    .clickable{
                        navigator?.push(AskForMoneyScreen())
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
                        navigator?.push(TransferToKyrgyzstanScreen())
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

@Preview
@Composable
private fun TransfersContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        TransfersContent()
    }
}