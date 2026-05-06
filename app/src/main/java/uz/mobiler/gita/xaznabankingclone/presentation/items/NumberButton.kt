package uz.mobiler.gita.xaznabankingclone.presentation.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

@Composable
fun NumberButton(number: String,modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number,
            fontSize = 30.sp,
            color = white,
            fontWeight = FontWeight.W600
        )
    }
}