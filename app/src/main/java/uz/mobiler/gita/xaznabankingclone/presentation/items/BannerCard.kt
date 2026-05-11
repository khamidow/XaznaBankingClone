package uz.mobiler.gita.xaznabankingclone.presentation.items

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import uz.mobiler.gita.xaznabankingclone.presentation.screens.home.BannerItem
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun BannerCard(item: BannerItem) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .width(screenWidth - 32.dp)
            .shadow(5.dp,RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xff2b7b54))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(enabled, RoundedCornerShape(14.dp))
                    .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 16.dp)
            ) {
                Text(
                    text = item.title,
                    color = white,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.subtitle,
                    color = white,
                    fontSize = 14.sp,
                    minLines = 2,
                    lineHeight = 1.em
                )
            }
            Column (
                modifier = Modifier.fillMaxHeight().padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = null,
                    modifier = Modifier.height(54.dp)
                )
                Spacer(Modifier.weight(1f))
            }

        }
    }
}