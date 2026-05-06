package uz.mobiler.gita.xaznabankingclone.presentation.items

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.mobiler.gita.presenter.viewModels.verifyScreen.VerifyContract
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.mainColor
import uz.mobiler.gita.xaznabankingclone.ui.theme.redColor
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

@SuppressLint("RememberInComposition")
@Composable
fun OtpInput(
    length: Int = 6,
    resetTrigger:Int,
    onCodeInputChanged: (String, Boolean) -> Unit
) {
    var otp by remember { mutableStateOf(List(length) { "" }) }
    val focusRequesters = List(length) { FocusRequester() }

    LaunchedEffect(resetTrigger) {
        otp = List(length) { "" }
        focusRequesters[0].requestFocus()
        onCodeInputChanged("", false)
    }

    val isComplete = otp.all { it.isNotEmpty() }
    val code = otp.joinToString("")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            repeat(length) { index ->
                val value = otp[index]

                val dotColor = if (isComplete) enabled else redColor

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .size(width = 0.dp, height = 54.dp)
                        .background(disabled, RoundedCornerShape(10.dp))
                        .border(0.5.dp, dotColor, RoundedCornerShape(10.dp))
                ) {

                    BasicTextField(
                        value = value,
                        onValueChange = { new ->
                            if (new.length <= 1 && new.all { it.isDigit() }) {

                                otp = otp.toMutableList().also {
                                    it[index] = new
                                }

                                if (new.isNotEmpty() && index < length - 1) {
                                    focusRequesters[index + 1].requestFocus()
                                }

                                val updatedCode = otp.joinToString("")
                                val complete = otp.all { it.isNotEmpty() }
                                onCodeInputChanged(updatedCode, complete)
                            }
                        },
                        textStyle = TextStyle(
                            color = white,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.W600
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(focusRequesters[index])
                            .onKeyEvent { event ->

                                if (event.key == Key.Backspace) {
                                    if (otp[index].isEmpty() && index > 0) {

                                        otp = otp.toMutableList().also {
                                            it[index - 1] = ""
                                        }

                                        focusRequesters[index - 1].requestFocus()

                                        val updatedCode = otp.joinToString("")
                                        val complete = otp.all { it.isNotEmpty() }
                                        onCodeInputChanged(updatedCode, complete)
                                    }
                                }

                                false
                            },
                        decorationBox = {
                            Box(contentAlignment = Alignment.Center) {
                                Text(value, color = white)
                            }
                        }
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .size(6.dp)
                            .background(dotColor, CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))
    }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}
