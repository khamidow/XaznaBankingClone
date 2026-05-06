package uz.mobiler.gita.xaznabankingclone.presentation.screens.changeNumber

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.presentation.items.UzPhoneInputThemed
import uz.mobiler.gita.xaznabankingclone.presentation.screens.verify.VerifyScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class ChangeNumberScreen : Screen {
    @Composable
    override fun Content() {
        ChangeNumberContent()
    }
}

@Composable
private fun ChangeNumberContent() {
    val navigator = LocalNavigator.current
    var isPhoneFilled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .safeDrawingPadding()
    ) {

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onBackground)
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
                    text = stringResource(R.string.change_number),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(modifier = Modifier.padding(horizontal = 18.dp)) {
                Text(
                    stringResource(R.string.phone_number),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600
                )
                Spacer(Modifier.height(8.dp))
                UzPhoneInputThemed(
                    onPhoneChanged = { phone, isComplete ->
                        isPhoneFilled = isComplete
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_info_green),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        stringResource(R.string.change_number_info),
                        fontSize = 12.5.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        lineHeight = 1.25.em,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
        Text(
            stringResource(R.string.next),
            color = white,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 18.dp, vertical = 26.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(isPhoneFilled, onClick = {
                    navigator?.push(VerifyScreen())
                })
                .background(if (isPhoneFilled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiaryFixedDim)
                .padding(11.dp)
        )
    }
}

@Preview
@Composable
private fun ChangeNumberContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        ChangeNumberContent()
    }
}
