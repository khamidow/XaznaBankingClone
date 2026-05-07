package uz.mobiler.gita.xaznabankingclone.presentation.screens.pinCode

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import uz.mobiler.gita.presenter.viewModels.pinCodeScreen.PinCodeContract
import uz.mobiler.gita.presenter.viewModels.pinCodeScreen.PinCodeViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.items.NumberButton
import uz.mobiler.gita.xaznabankingclone.presentation.screens.main.MainScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.redColor
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import kotlin.math.roundToInt

class PinCodeScreen : Screen {
    @Composable
    override fun Content() {
            val viewModel: PinCodeContract.ViewModel = hiltViewModel<PinCodeViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        viewModel.collectSideEffect {
            when (it) {
                is PinCodeContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is PinCodeContract.SideEffect.NavigateMain -> {
                    navigator?.push(MainScreen())
                }

                is PinCodeContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }
            }
        }
        PinCodeContent(
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun PinCodeContent(
    uiState: PinCodeContract.UiState,
    onEventDispatcher: (PinCodeContract.Intent) -> Unit
) {
    val navigator = LocalNavigator.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    var firstOrSecond by remember { mutableStateOf(1) }
    val biometricVerificationOffString = stringResource(R.string.biometric_verification_off)
    val makePinCodeString =
        if (firstOrSecond == 1) stringResource(R.string.make_pin_code)
        else stringResource(R.string.repeat_pin)
    var showErrorMessage by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }
    var isError by remember { mutableStateOf(false) }
    var switchState by remember { mutableStateOf(true) }
    var firstPin by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }

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
                if (firstOrSecond == 1) {
                    Log.d("TTT", "first")
                    showErrorMessage = false
                    firstPin = pin
                    pin = ""
                    firstOrSecond = 2
                } else {
                    Log.d("TTT", "second")
                    if (firstPin != pin) {
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
                        firstOrSecond = 1
                        firstPin = ""
                        pin = ""
                    } else {
                        Log.d("TTT", "correct")
                        pref.edit().putBoolean("face_id", switchState).apply()
                        pref.edit().putString("pin_code",pin).apply()
                        pref.edit().putBoolean("new_user",false).apply()
                        onEventDispatcher(PinCodeContract.Intent.OnSetPin(pin))
                    }
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
                makePinCodeString,
                color = white,
                fontSize = 26.sp,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 48.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(offsetX.value.roundToInt(), 0)
                    }
                    .padding(bottom = 18.dp),
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
                if (showErrorMessage) stringResource(R.string.pin_error_message) else "",
                fontSize = 14.sp,
                color = redColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 38.dp)
                    .background(
                        color = darkGreen,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {

                Column() {
                    Text(
                        text = stringResource(R.string.use_face_id_to_enter),
                        color = white,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    switchState,
                    onCheckedChange = {
                        switchState = !switchState
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .scale(0.7f)
                        .height(10.dp)
                )
            }

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
                                Toast.makeText(
                                    context,
                                    biometricVerificationOffString,
                                    Toast.LENGTH_SHORT
                                ).show()
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
//private fun PinCodeContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.DARK) {
//        PinCodeContent()
//    }
//}
