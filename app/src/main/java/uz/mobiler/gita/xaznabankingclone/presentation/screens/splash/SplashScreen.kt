package uz.mobiler.gita.xaznabankingclone.presentation.screens.splash

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.firstPin.FirstPinScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.language.LanguageScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightGradient
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
        val pin = pref.getString("pin_code", "") ?: ""
        val logoAlpha = remember { Animatable(0f) }
        val rectProgress = remember { Animatable(0f) }
        val arcProgress = remember { Animatable(0f) }
        val dotAlpha = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            delay(400)
            logoAlpha.animateTo(
                1f,
                animationSpec = tween(900, easing = EaseOut)
            )
            delay(100)
            rectProgress.animateTo(
                1f,
                animationSpec = tween(1100, easing = FastOutSlowInEasing)
            )
            arcProgress.animateTo(
                1f,
                animationSpec = tween(1200, easing = FastOutSlowInEasing)
            )
            dotAlpha.animateTo(1f, animationSpec = tween(250))
            delay(100)

            if (pin.isEmpty()) {
                navigator?.replaceAll(LanguageScreen())
            } else {
                navigator?.replaceAll(FirstPinScreen())
            }
        }

        Box(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(darkGradient, lightGradient)))
        ) {

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x551A4025),
                            Color(0x00071510)
                        ),
                        center = Offset(size.width * 0.65f, size.height * 0.28f),
                        radius = size.width * 0.85f
                    ),
                    radius = size.width * 0.85f,
                    center = Offset(size.width * 0.65f, size.height * 0.28f)
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-20).dp)
                    .graphicsLayer { alpha = logoAlpha.value },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Row(
                    modifier = Modifier
                        .padding(top = 76.dp)
                        .fillMaxWidth(), verticalAlignment = Alignment.Bottom
                ) {
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_xazna_logo_light),
                        contentDescription = null,
                        modifier = Modifier.size(56.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_xazna_name),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .height(51.5.dp),
                        colorFilter = ColorFilter.tint(white)
                    )
                    Spacer(Modifier.weight(1f))
                }
            }

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .align(Alignment.BottomCenter)
            ) {
                drawDecoLines(
                    rectProgress = rectProgress.value,
                    arcProgress = arcProgress.value,
                    dotAlpha = dotAlpha.value
                )
            }
        }

    }
}

private fun DrawScope.drawDecoLines(
    rectProgress: Float,
    arcProgress: Float,
    dotAlpha: Float
) {
    val w = size.width
    val h = size.height

    val rectSize  = w * 0.62f
    val rectLeft  = -rectSize * 0.28f
    val rectTop   = h * 0.22f
    val rectRight = rectLeft + rectSize
    val rectBot   = rectTop + rectSize

    val arcRadius  = w * 0.68f
    val arcCenterX = w * 1.0f
    val arcCenterY = h * 1.0f

    val startAngle = 180f
    val sweepFull  = 90f
    val sweepNow   = sweepFull * arcProgress

    val perimeter = rectSize * 4f
    val drawn = perimeter * rectProgress

    drawContext.canvas.save()

    val path = Path().apply {
        moveTo(rectLeft, rectTop)
        lineTo(rectRight, rectTop)
        lineTo(rectRight, rectBot)
        lineTo(rectLeft, rectBot)
        lineTo(rectLeft, rectTop)
        close()
    }

    val pm = android.graphics.PathMeasure(path.asAndroidPath(), false)
    val trimmed = android.graphics.Path()
    pm.getSegment(0f, drawn, trimmed, true)

    drawContext.canvas.nativeCanvas.drawPath(
        trimmed,
        android.graphics.Paint().apply {
            color = enabled.toArgb()
            style = android.graphics.Paint.Style.STROKE
            strokeWidth = 4.dp.toPx()
            strokeCap = android.graphics.Paint.Cap.ROUND
            strokeJoin = android.graphics.Paint.Join.ROUND
            isAntiAlias = true
        }
    )
    drawContext.canvas.restore()

    if (sweepNow > 0f) {
        drawArc(
            color = enabled,
            startAngle = startAngle,
            sweepAngle = sweepNow,
            useCenter = false,
            topLeft = Offset(arcCenterX - arcRadius, arcCenterY - arcRadius),
            size = Size(arcRadius * 2, arcRadius * 2),
            style = Stroke(
                width = 4.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }

    if (dotAlpha > 0f) {
        drawCircle(
            color = enabled.copy(alpha = dotAlpha),
            radius = 4.dp.toPx(),
            center = Offset(w - 3.dp.toPx(), h * 0.52f)
        )
    }
}