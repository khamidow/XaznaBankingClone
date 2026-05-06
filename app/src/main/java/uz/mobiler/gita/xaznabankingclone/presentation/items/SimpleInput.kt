package uz.mobiler.gita.xaznabankingclone.presentation.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

@Composable
fun SimpleInput(onTextChanged: (String, Boolean) -> Unit) {

    var textFieldValue by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }

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

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            BasicTextField(
                value = textFieldValue,
                maxLines = 1,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                    onTextChanged(textFieldValue, newValue.length >= 8)
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (isVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

                modifier = Modifier
                    .weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(
                    if (isVisible)
                        R.drawable.ic_eye_open
                    else
                        R.drawable.ic_eye_close
                ),
                colorFilter = ColorFilter.tint(white),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isVisible = !isVisible
                    }
            )
        }
    }
}