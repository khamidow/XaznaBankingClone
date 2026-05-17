package uz.mobiler.gita.xaznabankingclone.presentation.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

@Composable
fun KycDialog(onClick: () -> Unit, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.identification_bcg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(white),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(20.dp)
                        .clickable {
                            onDismiss()
                        }
                )
            }

            Text(
                stringResource(R.string.identificate_to_continue),
                color = white,
                fontWeight = FontWeight.W600,
                fontSize = 24.sp,
                lineHeight = 1.em,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            )

            Text(
                stringResource(R.string.identification_dialog_description),
                color = white,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 1.em,
                modifier = Modifier
                    .padding(top = 18.dp)
                    .fillMaxWidth()
            )
            Text(
                stringResource(R.string.pass_identification),
                color = white,
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 48.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(true, onClick = {
                        onClick()
                    })
                    .background(enabled)
                    .padding(11.dp)
            )
        }
    }
}
