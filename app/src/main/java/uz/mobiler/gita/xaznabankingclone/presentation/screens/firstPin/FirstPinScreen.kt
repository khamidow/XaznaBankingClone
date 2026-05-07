package uz.mobiler.gita.xaznabankingclone.presentation.screens.firstPin

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.biometric.BiometricManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.presenter.viewModels.firstPinScreen.FirstPinContract
import uz.mobiler.gita.presenter.viewModels.firstPinScreen.FirstPinViewModel
import uz.mobiler.gita.xaznabankingclone.MainActivity
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.items.NumberButton
import uz.mobiler.gita.xaznabankingclone.presentation.screens.language.LanguageScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.main.MainScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.redColor
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import kotlin.math.roundToInt

class FirstPinScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: FirstPinContract.ViewModel = hiltViewModel<FirstPinViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        viewModel.collectSideEffect {
            when (it) {
                is FirstPinContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is FirstPinContract.SideEffect.NavigateLanguage -> {
                    navigator?.replaceAll(LanguageScreen())
                }

                is FirstPinContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }
            }
        }
        FirstPinContent(
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun FirstPinContent(
    uiState: FirstPinContract.UiState,
    onEventDispatcher: (FirstPinContract.Intent) -> Unit
) {
    val navigator = LocalNavigator.current
    val context = LocalContext.current
    val biometricManager = BiometricManager.from(context)
    val canUseBiometric =
        biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) == BiometricManager.BIOMETRIC_SUCCESS
    val activity = LocalActivity.current as? MainActivity ?: return
    val coroutineScope = rememberCoroutineScope()
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    var tryCount by remember { mutableStateOf(3) }
    var showErrorMessage by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }
    var isError by remember { mutableStateOf(false) }
    val actualPin = pref.getString("pin_code", "")
    var pin by remember { mutableStateOf("") }

    var asked by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (canUseBiometric) {
            if (!asked && pref.getBoolean("face_id",true)) {
                asked = true
                activity.showBiometric()
            }
            activity.onBiometricSuccess = {
                navigator?.replaceAll(MainScreen())
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            activity.onBiometricSuccess = null
        }
    }

    val numbers = listOf(
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9"
    )

    val maxLength = 4

    @SuppressLint("ServiceCast")
    fun vibratePhone() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(200, VibrationEffect.EFFECT_HEAVY_CLICK)
            )
        } else {
            vibrator.vibrate(200)
        }
    }

    fun onNumberClick(number: String) {
        if (pin.length < maxLength) {
            pin += number

            if (pin.length == maxLength) {
                if (actualPin != pin) {
                    tryCount--
                    if (tryCount == 0) {
                        onEventDispatcher(FirstPinContract.Intent.OnLogOut)
                        pref.edit().putString("pin_code", "").apply()
                    }
                    Log.d("TTT", "wrong")
                    showErrorMessage = true
                    coroutineScope.launch {
                        vibratePhone()

                        isError = true
                        delay(99)
                        offsetX.animateTo(
                            targetValue = 0f,
                            animationSpec = keyframes {
                                durationMillis = 500
                                -30f at 50
                                30f at 100
                                -24f at 150
                                24f at 200
                                -16f at 250
                                16f at 300
                                -8f at 350
                                8f at 400
                                0f at 500
                            }
                        )
                        delay(99)
                        isError = false
                    }
                    pin = ""
                } else {
                    Log.d("TTT", "correct")
                    navigator?.replaceAll(MainScreen())
                }
            }
        }
    }

    fun onDelete() {
        if (pin.isNotEmpty()) {
            pin = pin.dropLast(1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(darkGradient, lightGradient)))
            .safeDrawingPadding()
            .padding(horizontal = 18.dp, vertical = 28.dp)
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .padding(top = 14.dp)
                    .fillMaxWidth()
            ) {
                Spacer(Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clickable {
                            onEventDispatcher(FirstPinContract.Intent.OnLogOut)
                            pref.edit().putString("pin_code", "").apply()
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_logout),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        stringResource(R.string.logout),
                        color = enabled,
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 78.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.Bottom
            ) {
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_xazna_logo_light),
                    contentDescription = null,
                    modifier = Modifier.size(42.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_xazna_name),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(36.dp),
                    colorFilter = ColorFilter.tint(white)
                )
                Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(offsetX.value.roundToInt(), 0)
                    }
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(Modifier.weight(1f))
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .size(17.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(
                                when {
                                    isError -> redColor
                                    index < pin.length -> enabled
                                    else -> disabled
                                }
                            )
                    )
                }
                Spacer(Modifier.weight(1f))
            }

            Text(
                if (showErrorMessage) stringResource(R.string.try_left_pin) + " " + tryCount else "",
                fontSize = 14.sp,
                color = redColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 28.dp)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier.padding(bottom = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                numbers.chunked(3).forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        row.forEach { num ->
                            NumberButton(
                                num,
                                modifier = Modifier
                                    .height(90.dp)
                                    .weight(1f)
                                    .clickable { onNumberClick(num) })
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Image(
                        painter = painterResource(R.drawable.ic_fingerprint),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(white),
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                            .clickable {
                                if (canUseBiometric) {
                                    activity.showBiometric()
                                }
                            }
                            .padding(26.dp)
                    )

                    NumberButton(
                        "0", Modifier
                            .height(90.dp)
                            .weight(1f)
                            .clickable { onNumberClick("0") })

                    Image(
                        painter = painterResource(R.drawable.ic_backspace),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(white),
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                            .clickable {
                                onDelete()
                            }
                            .padding(26.dp)
                    )
                }
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
//private fun FirstPinContentPreview() {
//    FirstPinContent()
//}