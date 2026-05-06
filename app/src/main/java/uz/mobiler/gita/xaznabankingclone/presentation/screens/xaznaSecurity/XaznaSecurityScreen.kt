package uz.mobiler.gita.xaznabankingclone.presentation.screens.xaznaSecurity

import android.content.Context
import android.view.WindowManager
import androidx.activity.compose.LocalActivity
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.MainActivity
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.presentation.items.SettingsItem
import uz.mobiler.gita.xaznabankingclone.presentation.screens.changeNumber.ChangeNumberScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme

class XaznaSecurityScreen : Screen {
    @Composable
    override fun Content() {
        XaznaSecurityContent()
    }
}

@Composable
private fun XaznaSecurityContent() {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val navigator = LocalNavigator.current
    val pref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    var screenshotRestrictionSwitchState by remember {
        mutableStateOf(
            pref.getBoolean(
                "screenshot_restrict",
                false
            )
        )
    }
    var transactionRestrictionSwitchState by remember {
        mutableStateOf(
            pref.getBoolean(
                "transaction_restriction",
                false
            )
        )
    }
    var hideBalanceSwitchState by remember {
        mutableStateOf(
            pref.getBoolean(
                "hide_balance",
                false
            )
        )
    }
    var voiceNotificationSwitchState by remember {
        mutableStateOf(
            pref.getBoolean(
                "voice_notification",
                false
            )
        )
    }
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
                    text = stringResource(R.string.xazna_security),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 6.dp, start = 18.dp, end = 18.dp, top = 20.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_screenshot),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(10.dp))

                Column() {
                    Text(
                        text = stringResource(R.string.screenshot_restrict),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600
                    )
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    screenshotRestrictionSwitchState,
                    onCheckedChange = {
                        if (!screenshotRestrictionSwitchState) {
                            activity?.window?.setFlags(
                                WindowManager.LayoutParams.FLAG_SECURE,
                                WindowManager.LayoutParams.FLAG_SECURE
                            )
                        } else {
                            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
                        }

                        screenshotRestrictionSwitchState = !screenshotRestrictionSwitchState
                        pref.edit()
                            .putBoolean("screenshot_restrict", screenshotRestrictionSwitchState)
                            .commit()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .scale(0.7f)
                        .height(10.dp)
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 6.dp, horizontal = 18.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_transaction),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(10.dp))

                Column() {
                    Text(
                        text = stringResource(R.string.transaction_restrict),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600
                    )
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    transactionRestrictionSwitchState,
                    onCheckedChange = {
                        transactionRestrictionSwitchState = !transactionRestrictionSwitchState
                        pref.edit().putBoolean(
                            "transaction_restriction",
                            transactionRestrictionSwitchState
                        )
                            .commit()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .scale(0.7f)
                        .height(10.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 6.dp, horizontal = 18.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_hide_eye),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(10.dp))

                Column() {
                    Text(
                        text = stringResource(R.string.hide_balance),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600
                    )
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    hideBalanceSwitchState,
                    onCheckedChange = {
                        hideBalanceSwitchState = !hideBalanceSwitchState
                        pref.edit().putBoolean("hide_balance", hideBalanceSwitchState)
                            .commit()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .scale(0.7f)
                        .height(10.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 6.dp, horizontal = 18.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(10.dp))

                Column() {
                    Text(
                        text = stringResource(R.string.voice_notifications),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600
                    )
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    voiceNotificationSwitchState,
                    onCheckedChange = {
                        voiceNotificationSwitchState = !voiceNotificationSwitchState
                        pref.edit().putBoolean("voice_notification", voiceNotificationSwitchState)
                            .commit()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .scale(0.7f)
                        .height(10.dp)
                )
            }


        }
    }
}

@Preview
@Composable
private fun XaznaSecurityContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        XaznaSecurityContent()
    }
}