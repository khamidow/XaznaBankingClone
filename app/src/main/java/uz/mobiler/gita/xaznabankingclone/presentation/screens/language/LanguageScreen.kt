package uz.mobiler.gita.xaznabankingclone.presentation.screens.language

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.appSettings.SettingsViewModel
import uz.mobiler.gita.xaznabankingclone.presentation.screens.phoneNumber.PhoneNumberScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class LanguageScreen : Screen {
    @Composable
    override fun Content() {
        LanguageContent()
    }
}

@Composable
private fun LanguageContent() {
    val navigator = LocalNavigator.current
    val localContext = LocalContext.current
    val activity = LocalActivity.current as ComponentActivity
    val viewModel: SettingsViewModel = viewModel(activity)
    val currentLanguage by viewModel.language.collectAsState()
    var selectedLanguageIndex by remember(currentLanguage) {
        mutableIntStateOf(
            when (currentLanguage) {
                "ru" -> 0
                "uz" -> 1
                "en" -> 2
                else -> 1
            }
        )
    }

    CompositionLocalProvider(LocalContext provides localContext) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.language_screen_bcg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .safeDrawingPadding()
                    .fillMaxSize()
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    stringResource(R.string.welcome_to_xazna),
                    color = white,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    stringResource(R.string.select_language_to_continue),
                    color = lightWhite,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                LazyRow(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                ) {
                    item { Spacer(modifier = Modifier.width(16.dp)) }
                    itemsIndexed(languages) { index, item ->
                        if (index == selectedLanguageIndex) {
                            Row(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .height(48.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xff31553b))
                                    .clip(RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(item.icon),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    item.name,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.W600,
                                    color = white,
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Image(
                                    painter = painterResource(R.drawable.ic_tick),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            Image(
                                painter = painterResource(item.icon),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xff243c2e))
                                    .clickable(true, onClick = {
                                        viewModel.setLanguage(
                                            when (index) {
                                                0 -> "ru"
                                                1 -> "uz"
                                                2 -> "en"
                                                else -> "uz"
                                            }
                                        )
                                    })
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                    }
                }
            }
            Text(
                stringResource(R.string.next).uppercase(),
                color = white,
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 18.dp, vertical = 28.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(true, onClick = {
                        navigator?.push(PhoneNumberScreen())
                    })
                    .border(1.dp, color = enabled, RoundedCornerShape(10.dp))
                    .padding(11.dp)
            )
        }
    }
}


@Preview
@Composable
private fun LanguageContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
        LanguageContent()
    }
}

private val languages = arrayListOf<LanguageScreenData>().apply {
    add(LanguageScreenData("Русский", R.drawable.ic_rus))
    add(LanguageScreenData("O'zbekcha", R.drawable.ic_uzb))
    add(LanguageScreenData("English", R.drawable.ic_eng))
}

private data class LanguageScreenData(
    val name: String,
    val icon: Int
)