package uz.mobiler.gita.xaznabankingclone.presentation.screens.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.presentation.items.BottomItem
import uz.mobiler.gita.xaznabankingclone.presentation.items.SettingsItem
import uz.mobiler.gita.xaznabankingclone.presentation.screens.profileDetail.ProfileDetailScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.security.SecurityScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.settings.SettingsScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme
import uz.mobiler.gita.xaznabankingclone.ui.theme.redColor

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        ProfileContent()
    }
}

@Composable
private fun ProfileContent() {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    val name = pref.getString("name", "") ?: ""
    val phoneNumber = pref.getString("phone_number", "") ?: ""
    var appType by remember { mutableStateOf(pref.getString("appType", "pro") ?: "pro") }
    var imageUri by remember { mutableStateOf(pref.getString("image_uri", null)) }

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
                            pref.edit().putString("appType", appType).apply()
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
                    text = stringResource(R.string.my_info),
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
                    .height(78.dp)
                    .padding(horizontal = 18.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable {
                        navigator?.push(ProfileDetailScreen())
                    }
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryFixed,
                        RoundedCornerShape(14.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(100.dp))
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_profile),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(100.dp))
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxHeight()
                ) {
                    Spacer(Modifier.weight(1f))
                    Text(
                        phoneNumber,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp
                    )
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (name == "")
                            Image(
                                painter = painterResource(R.drawable.ic_warning),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        Text(
                            if (name == "") "New user" else name,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
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
                            .clickable {
                                appType = "pro"
                            }
                            .background(
                                if (appType == "pro") MaterialTheme.colorScheme.background
                                else MaterialTheme.colorScheme.primaryContainer
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(Modifier.weight(1f))
                        Image(
                            painter = painterResource(R.drawable.ic_pro),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                if (appType != "pro") MaterialTheme.colorScheme.onPrimaryFixed
                                else MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            stringResource(R.string.xazna_pro),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        Spacer(Modifier.weight(1f))
                    }
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                appType = "lite"
                            }
                            .background(
                                if (appType == "lite") MaterialTheme.colorScheme.background
                                else MaterialTheme.colorScheme.primaryContainer
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(Modifier.weight(1f))
                        Image(
                            painter = painterResource(R.drawable.ic_lite),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                if (appType != "lite") MaterialTheme.colorScheme.onPrimaryFixed
                                else MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            stringResource(R.string.xazna_lite),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        Spacer(Modifier.weight(1f))
                    }
                }
                Image(
                    painter = painterResource(R.drawable.ic_transfers),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(22.dp)
                )
            }

            SettingsItem(R.drawable.ic_setting, stringResource(R.string.settings)) {
                navigator?.push(SettingsScreen())
            }
            SettingsItem(R.drawable.ic_transaction, stringResource(R.string.receipts)) {
            }
            SettingsItem(R.drawable.ic_transaction, stringResource(R.string.general_information)) {
            }
            SettingsItem(R.drawable.ic_call_center, stringResource(R.string.contact_the_bank)) {
            }
            SettingsItem(R.drawable.ic_share, stringResource(R.string.invite_friends)) {
            }
            SettingsItem(
                R.drawable.ic_security_check,
                stringResource(R.string.detection_of_malicious_applications)
            ) {}
            SettingsItem(R.drawable.ic_logout_red, stringResource(R.string.logout)) {
            }

            Spacer(modifier = Modifier.weight(1f))

            BottomItem(stringResource(R.string.public_offer)) {
            }
        }
    }
}

@Preview
@Composable
private fun ProfileContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        ProfileContent()
    }
}