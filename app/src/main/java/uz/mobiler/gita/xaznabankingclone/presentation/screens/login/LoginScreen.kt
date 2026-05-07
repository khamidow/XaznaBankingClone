package uz.mobiler.gita.xaznabankingclone.presentation.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.presentation.items.SimpleInput
import uz.mobiler.gita.xaznabankingclone.presentation.screens.appType.AppTypeScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.verify.VerifyScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabledText
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.limeGreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        LoginContent()
    }
}

@Composable
private fun LoginContent() {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    val actualPassword = pref.getString("password", "").toString()
    val wrongPasswordString = stringResource(R.string.wrong_password)
    var isPasswordFilled by remember { mutableStateOf(false) }
    var passwordState by remember { mutableStateOf("") }

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(disabled, disabled)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(1.dp, enabled, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 14.dp)
            ) {
                Text(
                    pref.getString("phone_number","")?:"",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Text(
                stringResource(R.string.password),
                color = white,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(top = 18.dp)
            )
            Spacer(Modifier.height(8.dp))
            SimpleInput(
                onTextChanged = { password, isComplete ->
                    passwordState = password
                    isPasswordFilled = isComplete
                }
            )

            Text(
                stringResource(R.string.forgot_password),
                color = limeGreen,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 38.dp)
                    .clickable {
                        navigator?.push(VerifyScreen(true))
                    }
            )
        }
        Text(
            stringResource(R.string.enter).uppercase(),
            color = if (isPasswordFilled) white else disabledText,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(isPasswordFilled, onClick = {
                    if (passwordState == actualPassword) {
                        navigator?.replaceAll(AppTypeScreen())
                    } else {
                        Toast.makeText(context, wrongPasswordString, Toast.LENGTH_SHORT).show()
                    }
                })
                .background(if (isPasswordFilled) enabled else disabled)
                .padding(11.dp)
        )
    }
}

@Preview
@Composable
private fun LoginContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
        LoginContent()
    }
}