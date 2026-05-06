package uz.mobiler.gita.xaznabankingclone.presentation.screens.infoAdvantage

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.presentation.screens.login.LoginScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.register.RegisterScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.verify.VerifyScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabledText
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class InfoAdvantageScreen : Screen {
    @Composable
    override fun Content() {
        InfoAdvantageContent()
    }
}

@Composable
private fun InfoAdvantageContent() {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    var checkboxState by remember { mutableStateOf(false) }
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(darkGradient, lightGradient)))
            .safeDrawingPadding()
            .padding(horizontal = 16.dp, vertical = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 96.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_face_scan),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(88.dp)
            )
            Text(
                stringResource(R.string.advantage),
                fontSize = 25.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                color = white,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 42.dp)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_wallet_advantage),
                    contentDescription = null,
                    modifier = Modifier.size(52.dp)
                )
                Column(modifier = Modifier.padding(start = 7.dp)) {
                    Text(
                        stringResource(R.string.your_financial_partner),
                        fontSize = 17.09.sp,
                        color = white
                    )
                    Text(
                        stringResource(R.string.adv_1_secription),
                        color = lightWhite,
                        fontSize = 12.9.sp,
                        lineHeight = 1.1.em,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_shield_advantage),
                    contentDescription = null,
                    modifier = Modifier.size(52.dp)
                )
                Column(modifier = Modifier.padding(start = 7.dp)) {
                    Text(
                        stringResource(R.string.save_money_reliable),
                        fontSize = 17.09.sp,
                        color = white
                    )
                    Text(
                        stringResource(R.string.adv_2_secription),
                        color = lightWhite,
                        fontSize = 12.9.sp,
                        lineHeight = 1.1.em,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_look_advantage),
                    contentDescription = null,
                    modifier = Modifier.size(52.dp)
                )
                Column(modifier = Modifier.padding(start = 7.dp)) {
                    Text(
                        stringResource(R.string.details_benefits_and_costs),
                        fontSize = 17.09.sp,
                        color = white
                    )
                    Text(
                        stringResource(R.string.adv_3_secription),
                        color = lightWhite,
                        fontSize = 12.9.sp,
                        lineHeight = 1.1.em,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_info),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    stringResource(R.string.advantage_info_small),
                    color = white,
                    fontSize = 12.5.sp,
                    modifier = Modifier.padding(start = 12.dp),
                    lineHeight = 1.1.em
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                    Checkbox(checkboxState, onCheckedChange = { checkboxState = !checkboxState })
                }
                Text(
                    stringResource(R.string.advantage_deal),
                    color = white,
                    fontSize = 14.7.sp,
                    modifier = Modifier.padding(start = 12.dp),
                )
            }
        }

        Text(
            stringResource(R.string.register),
            color = if (checkboxState) white else disabledText,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(checkboxState, onClick = {
                    if (pref.getBoolean("new_user", true)) {
                        navigator?.push(RegisterScreen())
                    } else {
                        navigator?.push(LoginScreen())
                    }
                })
                .background(if (checkboxState) enabled else disabled)
                .padding(11.dp)
        )
    }
}

@Preview
@Composable
private fun InfoAdvantageContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        InfoAdvantageContent()
    }
}