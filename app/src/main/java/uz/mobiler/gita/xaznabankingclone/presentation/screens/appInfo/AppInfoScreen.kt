package uz.mobiler.gita.xaznabankingclone.presentation.screens.appInfo

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
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
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme

class AppInfoScreen : Screen {
    @Composable
    override fun Content() {
        AppInfoContent()
    }
}

@Composable
private fun AppInfoContent() {
    val navigator = LocalNavigator.current
    val uriHandler = LocalUriHandler.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
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
                    text = stringResource(R.string.about_app),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_xazna_logo),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_xazna_name),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 4.dp)
                        .height(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
                Spacer(Modifier.weight(1f))
            }
            Text(
                "1.0.53",
                color = MaterialTheme.colorScheme.onPrimaryFixed,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                fontSize = 12.sp
            )
            Text(
                stringResource(R.string.about_app_description),
                fontSize = 13.5.sp,
                color = MaterialTheme.colorScheme.onPrimaryFixed,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                lineHeight = 1.15.em
            )
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_telegram),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(24.dp)
                        .clickable {
                            uriHandler.openUri("https://t.me/xaznauz")
                        }
                )
                Image(
                    painter = painterResource(R.drawable.ic_facebook),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(24.dp)
                        .clickable {
                            uriHandler.openUri("https://www.facebook.com/xazna/")
                        }
                )
                Image(
                    painter = painterResource(R.drawable.ic_instagram),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(24.dp)
                        .clickable {
                            uriHandler.openUri("https://www.instagram.com/xaznauz/")
                        }
                )
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
private fun AppInfoContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        AppInfoContent()
    }
}