package uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class NoConnectionScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(white)
                .safeDrawingPadding()
                .padding(18.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_no_connection),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 24.dp)
                    .size(92.dp)
            )
            Text(
                stringResource(R.string.try_again),
                color = white,
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(true, onClick = {
                        navigator?.pop()
                    })
                    .background(enabled)
                    .padding(11.dp)
            )
        }
    }
}