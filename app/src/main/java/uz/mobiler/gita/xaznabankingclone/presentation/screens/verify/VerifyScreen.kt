package uz.mobiler.gita.xaznabankingclone.presentation.screens.verify

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.presenter.viewModels.verifyScreen.VerifyContract
import uz.mobiler.gita.presenter.viewModels.verifyScreen.VerifyViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.items.OtpInput
import uz.mobiler.gita.xaznabankingclone.presentation.screens.infoAdvantage.InfoAdvantageScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.register.RegisterScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabledText
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class VerifyScreen(private val shouldSendAgain: Boolean = false) : Screen {
    @Composable
    override fun Content() {
        val viewModel: VerifyViewModel = hiltViewModel<VerifyViewModel>()
        val uiState = viewModel.collectAsState()
        val navigator = LocalNavigator.current
        val context = LocalContext.current
        val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
        val phoneNumber = pref.getString("phone_number", "").toString()
        viewModel.collectSideEffect {
            when (it) {
                is VerifyContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is VerifyContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }
            }
        }
        if (shouldSendAgain) {
            LaunchedEffect(Unit) {
                viewModel.load(phoneNumber)
            }
        }
        VerifyScreenContent(
            shouldSendAgain,
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun VerifyScreenContent(
    shouldSendAgain: Boolean = false,
    uiState: VerifyContract.UiState,
    onEventDispatcher: (VerifyContract.Intent) -> Unit
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    val phoneNumber = pref.getString("phone_number", "").toString()
    var resetTrigger by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(119) }
    var isEnabled by remember { mutableStateOf(false) }
    var code by remember { mutableStateOf("") }

    LaunchedEffect(timeLeft) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
    }

    val formatted = String.format(
        "%02d:%02d",
        timeLeft / 60,
        timeLeft % 60
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(darkGradient, lightGradient)))
            .safeDrawingPadding()
            .padding(horizontal = 16.dp, vertical = 28.dp)
    ) {
        Column() {
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(51.dp))
                    .padding(6.dp)
                    .clickable(true, onClick = {
                        navigator?.pop()
                    })
            )
            Text(
                stringResource(R.string.verify_account),
                color = white,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 28.dp)
                    .fillMaxWidth()
            )
            Text(
                stringResource(R.string.verification_code_is_send_to_phone),
                color = lightWhite,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 1.1.em,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .padding(top = 28.dp, bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, color = disabled, RoundedCornerShape(10.dp))
                    .clickable(true, onClick = {
                        navigator?.pop()
                    })
                    .padding(14.dp)
            ) {
                Text(
                    phoneNumber,
                    color = white,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp
                )
                Image(
                    painter = painterResource(R.drawable.ic_edit_phone),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                        .size(18.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 26.dp, end = 26.dp)
            ) {
                OtpInput(resetTrigger = resetTrigger, onCodeInputChanged = { newCode, complete ->
                    code = newCode
                    isEnabled = complete
                })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            disabled,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable(timeLeft == 0, onClick = {
                            timeLeft = 119
                            resetTrigger++
                            onEventDispatcher(VerifyContract.Intent.OnSendOtp(phoneNumber))
                        })
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_resend),
                        contentDescription = null,
                        Modifier
                            .padding(end = 6.dp)
                            .size(14.dp)
                    )
                    Text(
                        stringResource(R.string.resend),
                        color = if (timeLeft > 0) lightWhite else white,
                        fontSize = 15.sp
                    )
                }

                Text(
                    formatted,
                    color = white,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .border(1.dp, color = disabled, RoundedCornerShape(10.dp))
                        .padding(horizontal = 6.dp, vertical = 5.dp)
                )
                Spacer(Modifier.weight(1f))
            }
        }

        Text(
            stringResource(R.string.confirm).uppercase(),
            color = if (isEnabled) white else disabledText,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(isEnabled, onClick = {
                    onEventDispatcher(VerifyContract.Intent.OnVerifyOtp(phoneNumber, code))
                })
                .background(if (isEnabled) enabled else disabled)
                .padding(11.dp)
        )
        if (uiState.success) {
//            pref.edit().putBoolean("new_user", true).commit()
            if (shouldSendAgain) {
                navigator?.replaceAll(RegisterScreen())
            } else {
                navigator?.replaceAll(InfoAdvantageScreen())
            }
        }
    }
    if (uiState.loading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(loadingTransparentBcg)
        ) {
            val imageLoader = ImageLoader.Builder(context)
                .components { add(GifDecoder.Factory()) }
                .build()
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.gif_loading)
                    .build(),
                contentDescription = null,
                imageLoader = imageLoader,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(84.dp)
            )
        }
    }
}

//@Preview
//@Composable
//private fun VerifyScreenContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
//        VerifyScreenContent()
//    }
//}
