package uz.mobiler.gita.xaznabankingclone.presentation.screens.universalPayment

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme

class UniversalPaymentScreen : Screen {
    @Composable
    override fun Content() {
        UniversalPaymentContent()
    }
}

@Composable
private fun UniversalPaymentContent() {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val doNotWork = stringResource(R.string.do_not_work)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryFixedDim)
            .safeDrawingPadding()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryFixedDim)
            ) {
                Box(
                    modifier = Modifier
                        .padding(18.dp)
                        .size(28.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimaryFixed,
                            RoundedCornerShape(6.dp)
                        )
                        .clickable {
                            navigator?.pop()
                        }
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed)
                    )
                }

                Text(
                    text = stringResource(R.string.international_transfers),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 6.dp, horizontal = 18.dp)
                    .shadow(7.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        Toast.makeText(context, doNotWork, Toast.LENGTH_SHORT).show()
                    }
                    .background(
                        color = MaterialTheme.colorScheme.primaryFixedDim,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.img),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(Modifier.width(10.dp))

                Column() {
                    Text(
                        text = "Universal Payment Transfer",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600
                    )
                }
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun UniversalPaymentContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
        UniversalPaymentContent()
    }
}