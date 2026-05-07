package uz.mobiler.gita.xaznabankingclone.presentation.screens.phoneNumber

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.presenter.viewModels.phoneScreen.PhoneContract
import uz.mobiler.gita.presenter.viewModels.phoneScreen.PhoneViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.items.UzPhoneInput
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.verify.VerifyScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabledText
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class PhoneNumberScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: PhoneContract.ViewModel = hiltViewModel<PhoneViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        viewModel.collectSideEffect {
            when (it) {
                is PhoneContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is PhoneContract.SideEffect.NavigateVerify -> {
                    navigator?.push(VerifyScreen())
                }
                is PhoneContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }
            }
        }
        PhoneNumberContent(
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun PhoneNumberContent(
    uiState: PhoneContract.UiState,
    onEventDispatcher: (PhoneContract.Intent) -> Unit
) {
     val navigator = LocalNavigator.current
    val context = LocalContext.current
    var isPhoneFilled by remember { mutableStateOf(false) }
    var phoneState by remember { mutableStateOf("") }
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)

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
                stringResource(R.string.enter),
                color = white,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 28.dp)
                    .fillMaxWidth()
            )
            Text(
                stringResource(R.string.enter_app_to_get_comfort),
                color = lightWhite,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 1.1.em,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            )

            Text(
                stringResource(R.string.phone_number),
                color = white,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(top = 38.dp)
            )
            Spacer(Modifier.height(8.dp))
            UzPhoneInput(
                onPhoneChanged = { phone, isComplete ->
                    phoneState = phone
                    isPhoneFilled = isComplete
                }
            )
        }
        Text(
            stringResource(R.string.next).uppercase(),
            color = if (isPhoneFilled) white else disabledText,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(isPhoneFilled, onClick = {
                    onEventDispatcher(PhoneContract.Intent.OnSendOtp(phoneState))
                    pref.edit().putString("phone_number", phoneState).commit()
                })
                .background(if (isPhoneFilled) enabled else disabled)
                .padding(11.dp)
        )
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
//private fun PhoneNumberContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
//        PhoneNumberContent()
//    }
//}