package uz.mobiler.gita.xaznabankingclone.presentation.screens.services

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.presentation.screens.profileDetail.ProfileDetailScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.settings.SettingsScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme

class ServicesScreen : Screen {
    @Composable
    override fun Content() {
        ServicesContent()
    }
}

@Composable
private fun ServicesContent() {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow.parent
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    val name = pref.getString("name", "") ?: ""
    val phoneNumber = pref.getString("phone_number", "") ?: ""
    var imageUri by remember { mutableStateOf(pref.getString("image_uri", null)) }

    val listOfServices = mutableListOf<ServiceData>().apply {
        add(ServiceData(R.drawable.ic_katm,"KATM"))
        add(ServiceData(R.drawable.ic_avia,"xazna avia"))
        add(ServiceData(R.drawable.ic_donor,stringResource(R.string.social_services)))
        add(ServiceData(R.drawable.ic_bank,stringResource(R.string.my_bank)))
        add(ServiceData(R.drawable.ic_home,stringResource(R.string.my_home)))
        add(ServiceData(R.drawable.ic_car,stringResource(R.string.my_car)))
        add(ServiceData(R.drawable.ic_lightning,"Xazna drive"))
        add(ServiceData(R.drawable.ic_humo,stringResource(R.string.government_services)))
        add(ServiceData(R.drawable.ic_pension,stringResource(R.string.my_pension)))
        add(ServiceData(R.drawable.ic_soliq,stringResource(R.string.tax_debts)))
        add(ServiceData(R.drawable.ic_transaction,stringResource(R.string.my_applications)))
        add(ServiceData(R.drawable.ic_quality_assesment,stringResource(R.string.service_quality_assesment)))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(18.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.ic_xazna_logo),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
            Image(
                painter = painterResource(R.drawable.ic_xazna_name),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier
                    .padding(top = 5.dp, start = 4.dp)
                    .height(20.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.ic_setting),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navigator?.push(SettingsScreen())
                    }
            )
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(78.dp)
                .clip(RoundedCornerShape(14.dp))
                .clickable {
                    navigator?.push(ProfileDetailScreen())
                }
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.primary,
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

        Text(
            stringResource(R.string.services),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.W600,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(listOfServices){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 6.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { }
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Image(
                        painter = painterResource(it.image),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier.size(26.dp)
                    )

                    Spacer(Modifier.width(12.dp))

                    Column() {
                        Text(
                            text = it.name,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.W600
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ServicesContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        ServicesContent()
    }
}

private data class ServiceData(
    val image:Int,
    val name:String
)

