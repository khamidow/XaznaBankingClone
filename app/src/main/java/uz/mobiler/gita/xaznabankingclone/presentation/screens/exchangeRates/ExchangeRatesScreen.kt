package uz.mobiler.gita.xaznabankingclone.presentation.screens.exchangeRates

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.core.models.ExchangeData
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme
import uz.mobiler.gita.xaznabankingclone.utils.formatDate

class ExchangeRatesScreen(private val exchangeRates: List<ExchangeData>) : Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        ExchangeRatesContent(exchangeRates)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ExchangeRatesContent(exchangeRates: List<ExchangeData>) {
    val navigator = LocalNavigator.current

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
                    text = stringResource(R.string.exchange_rates),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                stringResource(R.string.last_update) + " " + formatDate(exchangeRates[0].updatedAt),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            ) {
                items(exchangeRates) {
                    val icon = when (it.currency) {
                        "USD" -> R.drawable.ic_usa
                        "EUR" -> R.drawable.ic_euro
                        "RUB" -> R.drawable.ic_rus
                        "GBP" -> R.drawable.ic_eng
                        "CNY" -> R.drawable.ic_china
                        else -> R.drawable.ic_uzb
                    }
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 18.dp, vertical = 6.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                color = MaterialTheme.colorScheme.onBackground,
                                RoundedCornerShape(14.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(icon),
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .size(24.dp)
                            )
                            Text(
                                it.currency,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.W600,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 14.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                stringResource(R.string.mb_rate),
                                color = MaterialTheme.colorScheme.onPrimaryFixed,
                                fontWeight = FontWeight.W500,
                                fontSize = 13.sp,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                            Text(
                                stringResource(R.string.buy),
                                color = MaterialTheme.colorScheme.onPrimaryFixed,
                                fontWeight = FontWeight.W500,
                                fontSize = 13.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                            Text(
                                stringResource(R.string.sell),
                                color = MaterialTheme.colorScheme.onPrimaryFixed,
                                fontWeight = FontWeight.W500,
                                fontSize = 13.sp,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                ((it.buy + it.sell) / 2).toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.W500,
                                fontSize = 16.sp,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                            Text(
                                it.buy.toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.W500,
                                fontSize = 16.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                            Text(
                                it.sell.toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.W500,
                                fontSize = 16.sp,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ExchangeRatesContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        ExchangeRatesContent(
            listOf(
                ExchangeData(
                    "UZS",
                    12700.toDouble(),
                    12750.toDouble(),
                    "2026-05-16T14:46:26.192Z"
                ),
                ExchangeData(
                    "EUR",
                    12700.toDouble(),
                    12750.toDouble(),
                    "2026-05-16T14:46:26.192Z"
                )
            )
        )
    }
}