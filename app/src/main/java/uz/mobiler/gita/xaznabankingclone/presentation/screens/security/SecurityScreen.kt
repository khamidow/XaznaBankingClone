package uz.mobiler.gita.xaznabankingclone.presentation.screens.security

import android.content.Context
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
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.presentation.items.SettingsItem
import uz.mobiler.gita.xaznabankingclone.presentation.screens.changeNumber.ChangeNumberScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.xaznaSecurity.XaznaSecurityScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme

class SecurityScreen : Screen {
    @Composable
    override fun Content() {
        SecurityContent()
    }
}

@Composable
private fun SecurityContent() {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val pref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    var biometrySwitchState by remember {
        mutableStateOf(
            pref.getBoolean(
                "face_id",
                true
            )
        )
    }
    var confirmPaymentSwitchState by remember {
        mutableStateOf(
            pref.getBoolean(
                "confirm_payment",
                false
            )
        )
    }
    var useEmailState by remember { mutableStateOf(pref.getBoolean("use_email", false)) }
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
                    text = stringResource(R.string.security),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(28.dp))

            SettingsItem(R.drawable.ic_phone, stringResource(R.string.change_number)) {
                navigator?.push(ChangeNumberScreen())
            }
            SettingsItem(R.drawable.ic_key, stringResource(R.string.change_password)) {

            }
            SettingsItem(R.drawable.ic_lock, stringResource(R.string.change_pin)) {

            }
            SettingsItem(R.drawable.ic_device, stringResource(R.string.reliable_devices)) {

            }
            SettingsItem(R.drawable.ic_scan, stringResource(R.string.connect_dektop)) {

            }
            SettingsItem(R.drawable.ic_cloud, stringResource(R.string.xazna_security)) {
                navigator?.push(XaznaSecurityScreen())
            }

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
                    painter = painterResource(R.drawable.ic_face_id),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(10.dp))

                Column() {
                    Text(
                        text = stringResource(R.string.enter_with_biometry),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600
                    )
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    biometrySwitchState,
                    onCheckedChange = {
                        biometrySwitchState = !biometrySwitchState
                        pref.edit().putBoolean("biometry_enterence", biometrySwitchState).commit()
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
                    painter = painterResource(R.drawable.ic_security_check),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(10.dp))

                Column() {
                    Text(
                        text = stringResource(R.string.confirm_payment),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600
                    )
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    confirmPaymentSwitchState,
                    onCheckedChange = {
                        confirmPaymentSwitchState = !confirmPaymentSwitchState
                        pref.edit().putBoolean("confirm_payment", confirmPaymentSwitchState)
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
                    .padding(end = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(top = 6.dp, bottom = 6.dp, start = 18.dp, end = 4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_mail_box),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(Modifier.width(10.dp))

                    Column() {
                        Text(
                            text = stringResource(R.string.use_email),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.W600
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Switch(
                        useEmailState,
                        onCheckedChange = {
                            useEmailState = !useEmailState
                            pref.edit().putBoolean("use_email", useEmailState).commit()
                        },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .scale(0.7f)
                            .height(10.dp)
                    )
                }

                Image(
                    painter = painterResource(R.drawable.ic_edit_phone),
                    contentDescription = null,
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .padding(12.dp)
                )
            }

        }
    }
}

@Preview
@Composable
private fun SecurityContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        SecurityContent()
    }
}