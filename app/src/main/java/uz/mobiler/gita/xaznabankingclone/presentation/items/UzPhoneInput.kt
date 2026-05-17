package uz.mobiler.gita.xaznabankingclone.presentation.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.utils.formatUzPhone

@Composable
fun UzPhoneInput(onPhoneChanged: (String, Boolean) -> Unit) {
    var rawDigits by remember { mutableStateOf("") }
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue("+998 ")
        )
    }


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
        BasicTextField(
            value = textFieldValue,
            onValueChange = { newValue ->

                val oldText = textFieldValue.text
                val isDeleting = newValue.text.length < oldText.length

                val newDigits = newValue.text.filter { it.isDigit() }
                val oldDigits = rawDigits

                val cleanDigits = if (newDigits.startsWith("998")) {
                    newDigits.drop(3)
                } else newDigits

                rawDigits = if (isDeleting) {
                    oldDigits.dropLast(1)
                } else {
                    cleanDigits.take(9)
                }

                val formatted = formatUzPhone(rawDigits)

                textFieldValue = TextFieldValue(
                    text = formatted,
                    selection = TextRange(formatted.length)
                )

                val isComplete = rawDigits.length == 9
                onPhoneChanged("+998$rawDigits", isComplete)
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 18.sp
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                innerTextField()
            }
        )
    }
}
