package uz.mobiler.gita.xaznabankingclone.presentation.screens.identification

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.identificationCamera.FaceCameraScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.black
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.light
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.mainColor
import uz.mobiler.gita.xaznabankingclone.ui.theme.redColor
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import uz.mobiler.gita.xaznabankingclone.utils.formatBirthDate
import uz.mobiler.gita.xaznabankingclone.utils.formatPassportSeries

class IdentificationScreen : Screen {
    @Composable
    override fun Content() {
        IdentificationContent()
    }
}

@Composable
private fun IdentificationContent() {
    val navigator = LocalNavigator.current
    var passportSeries by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf(TextFieldValue("")) }
    var isResident by remember { mutableStateOf(true) }
    val isPassportValid =
        passportSeries.length == 9 &&
                passportSeries.take(2).all { it.isLetter() } &&
                passportSeries.drop(2).all { it.isDigit() }

    val isBirthDateValid = birthDate.text.length == 10

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .safeDrawingPadding()
            .padding(horizontal = 20.dp)
    ) {

        Spacer(Modifier.height(12.dp))
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "Back",
            tint = black,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    navigator?.pop()
                }
        )

        Spacer(Modifier.height(28.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.ic_xazna_logo),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Image(
                painter = painterResource(R.drawable.ic_xazna_name),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 14.dp, top = 16.dp)
                    .height(60.dp)
            )
            Spacer(Modifier.weight(1f))
        }

        Spacer(Modifier.height(36.dp))

        Text(
            text = stringResource(R.string.login_or_register),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = black,
            lineHeight = 32.sp
        )

        Spacer(Modifier.height(28.dp))

        Text(
            text = stringResource(R.string.passport_series),
            fontSize = 16.sp,
            color = black,
            fontWeight = FontWeight.W600
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = passportSeries,
            onValueChange = {
                passportSeries = formatPassportSeries(it)
            },
            placeholder = {
                Text(
                    text = "AA1234567",
                    color = lightWhite,
                    fontSize = 18.sp
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_qr_code),
                        contentDescription = "Scan QR",
                        tint = redColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = black
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = white,
                unfocusedContainerColor = white,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = mainColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, lightWhite, RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Tug'ilgan kun",
            fontSize = 16.sp,
            color = black,
            fontWeight = FontWeight.W600
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = birthDate,
            onValueChange = {
                val formatted = formatBirthDate(it.text)

                birthDate = TextFieldValue(
                    text = formatted,
                    selection = TextRange(formatted.length) // cursor always at end
                )
            },
            placeholder = {
                Text(
                    text = "kk.oo.yyyy",
                    color = lightWhite,
                    fontSize = 18.sp
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = black
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = white,
                unfocusedContainerColor = white,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = mainColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, lightWhite, RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
        )

        Spacer(Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { isResident = !isResident }
        ) {
            Checkbox(
                checked = isResident,
                onCheckedChange = { isResident = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = mainColor,
                    uncheckedColor = lightWhite,
                    checkmarkColor = white
                )
            )
            Text(
                text = stringResource(R.string.i_am_resident),
                fontSize = 16.sp,
                color = black,
            )
        }

        Spacer(Modifier.height(28.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(if (isPassportValid && isBirthDateValid) darkGreen else light)
                .clickable(enabled = isPassportValid && isBirthDateValid) {
                    navigator?.push(FaceCameraScreen(passportSeries, birthDate.text))
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.continue_kyc),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}

@Preview
@Composable
private fun IdentificationContentPreview() {
    IdentificationContent()
}