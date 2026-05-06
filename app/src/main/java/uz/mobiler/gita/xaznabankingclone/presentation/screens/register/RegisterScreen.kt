package uz.mobiler.gita.xaznabankingclone.presentation.screens.register

import android.annotation.SuppressLint
import android.content.Context
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
import uz.mobiler.gita.xaznabankingclone.presentation.items.ValidationItem
import uz.mobiler.gita.xaznabankingclone.presentation.screens.phoneNumber.PhoneNumberScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabledText
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.limeGreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class RegisterScreen : Screen {
    @Composable
    override fun Content() {
        RegisterContent()
    }
}

@SuppressLint("ApplySharedPref")
@Composable
private fun RegisterContent() {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)

    var validation by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    val validationFun = validatePassword(validation)

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
                stringResource(R.string.new_password),
                color = white,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 28.dp)
                    .fillMaxWidth()
            )
            Text(
                stringResource(R.string.make_new_parol_and_verify),
                color = lightWhite,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 1.1.em,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            )

            Text(
                stringResource(R.string.new_password),
                color = white,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(top = 74.dp)
            )
            Spacer(Modifier.height(8.dp))
            SimpleInput(
                onTextChanged = { text, isComplete ->
                    validation = text
                }
            )

            Text(
                stringResource(R.string.verify_password),
                color = white,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(top = 14.dp)
            )
            Spacer(Modifier.height(8.dp))
            SimpleInput(
                onTextChanged = { text, isComplete ->
                    confirm = text
                }
            )

            Spacer(Modifier.height(18.dp))

            ValidationItem(stringResource(R.string.chars_8_to_15), validationFun.lengthValid)
            ValidationItem(
                stringResource(R.string.at_least_1_big_letter),
                validationFun.hasUpperCase
            )
            ValidationItem(
                stringResource(R.string.at_least_1_small_letter),
                validationFun.hasLowerCase
            )
            ValidationItem(stringResource(R.string.at_least_1_digit), validationFun.hasDigit)
        }

        Text(
            stringResource(R.string.confirm).uppercase(),
            color = if (validationFun.isAllValid && confirm == validation) white else disabledText,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(validationFun.isAllValid && confirm == validation, onClick = {
                    pref.edit().putString("password", validation).apply()
                    pref.edit().putBoolean("new_user", false).apply()
                    navigator?.push(PhoneNumberScreen())
                })
                .background(if (validationFun.isAllValid && confirm == validation) enabled else disabled)
                .padding(11.dp)
        )
    }
}

@Preview
@Composable
private fun RegisterContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        RegisterContent()
    }
}

data class PasswordValidation(
    val lengthValid: Boolean,
    val hasUpperCase: Boolean,
    val hasLowerCase: Boolean,
    val hasDigit: Boolean
) {
    val isAllValid: Boolean
        get() = lengthValid && hasUpperCase && hasLowerCase && hasDigit
}

fun validatePassword(password: String): PasswordValidation {
    return PasswordValidation(
        lengthValid = password.length in 8..15,
        hasUpperCase = password.any { it.isUpperCase() },
        hasLowerCase = password.any { it.isLowerCase() },
        hasDigit = password.any { it.isDigit() }
    )
}