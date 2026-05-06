package uz.mobiler.gita.xaznabankingclone.presentation.screens.appType

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
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
import uz.mobiler.gita.xaznabankingclone.presentation.screens.login.LoginScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.pinCode.PinCodeScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.register.RegisterScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.disabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import kotlin.io.path.Path
import kotlin.io.path.moveTo

class AppTypeScreen : Screen {
    @Composable
    override fun Content() {
        AppTypeContent()
    }
}

@Composable
private fun AppTypeContent() {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    val lite = stringResource(R.string.xazna_lite)
    val pro = stringResource(R.string.xazna_pro)
    val liteDescription = stringResource(R.string.lite_decrioption)
    val proDescription = stringResource(R.string.pro_decrioption)
    var appTypeState by remember { mutableStateOf(lite) }
    var descriptionState = if (appTypeState == lite) liteDescription else proDescription

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(darkGradient, lightGradient)))
    ) {
        BoxWithConstraints {
            val widthPx = constraints.maxWidth.toFloat()
            val heightPx = constraints.maxHeight.toFloat()

            Canvas(modifier = Modifier.fillMaxSize()) {

                val stroke = 3.dp.toPx()
                val color = Color.White.copy(alpha = 0.1f)
                val radius = widthPx

                drawRect(
                    color = color,
                    topLeft = Offset(0f, heightPx - widthPx),
                    size = Size(widthPx / 2f, widthPx),
                    style = Stroke(width = stroke)
                )

                drawArc(
                    color = color,
                    startAngle = 90f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(
                        x = widthPx - radius / 2,
                        y = heightPx - radius
                    ),
                    size = Size(radius, radius),
                    style = Stroke(width = stroke)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 16.dp, vertical = 28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 82.dp),
            ) {
                Text(
                    stringResource(R.string.choose_app_type),
                    color = white,
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    appTypeState,
                    color = white,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.5.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                Text(
                    descriptionState,
                    color = lightWhite,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 1.1.em,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (appTypeState == pro) lightGradient else disabled,
                                RoundedCornerShape(10.dp)
                            )
                            .border(
                                2.dp, if (appTypeState == pro) enabled else disabled,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                appTypeState = pro
                            }
                            .padding(vertical = 22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_pro),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            pro,
                            fontSize = 15.7.sp,
                            color = white,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (appTypeState == lite) lightGradient else disabled,
                                RoundedCornerShape(10.dp)
                            )
                            .border(
                                2.dp, if (appTypeState == lite) enabled else disabled,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                appTypeState = lite
                            }
                            .padding(vertical = 22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_lite),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            lite,
                            fontSize = 15.7.sp,
                            color = white,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Text(
                stringResource(R.string.next),
                color = white,
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(true, onClick = {
                        pref.edit()
                            .putString("appType", if (appTypeState == lite) "lite" else "pro")
                            .apply()
                        navigator?.push(PinCodeScreen())
                    })
                    .background(enabled)
                    .padding(11.dp)
            )
        }
    }

}

@Preview
@Composable
private fun AppTypeContentPreview() {
    AppTypeContent()
}