package uz.mobiler.gita.xaznabankingclone.presentation.screens.identificationCamera

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.presenter.viewModels.kcyCameraScreen.KycCameraContract
import uz.mobiler.gita.presenter.viewModels.kcyCameraScreen.KycCameraViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.main.MainScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.black
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.redColor
import uz.mobiler.gita.xaznabankingclone.ui.theme.white
import uz.mobiler.gita.xaznabankingclone.utils.imageToBase64
import uz.mobiler.gita.xaznabankingclone.utils.toApiDateFormat
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors


private sealed class FaceState {
    object Scanning : FaceState()
    object Detected : FaceState()
}


class FaceCameraScreen(private val passportSeries: String, private val birthDate: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel: KycCameraContract.ViewModel = hiltViewModel<KycCameraViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow.parent
        viewModel.collectSideEffect {
            when (it) {
                is KycCameraContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is KycCameraContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }

                is KycCameraContract.SideEffect.NavigateToHome -> {
                    navigator?.replaceAll(MainScreen())
                }
            }
        }
        CameraScreen(
            passportSeries,
            birthDate,
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}


@Composable
private fun PermissionRationaleScreen(onBack: () -> Unit, onAllow: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .safeDrawingPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "Back",
            tint = black,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Start)
                .clickable { onBack() }
        )

        Spacer(Modifier.weight(1f))

        Icon(
            painter = painterResource(android.R.drawable.ic_menu_camera),
            contentDescription = null,
            tint = Color(0xFFE87722),
            modifier = Modifier.size(72.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.need_camera_permission),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = black,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.need_camera_permission_to_identificate),
            fontSize = 15.sp,
            color = Color(0xFF666666),
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(Modifier.height(36.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(darkGreen)
                .clickable { onAllow() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.allow),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.weight(1f))
    }
}


@Composable
private fun PermissionWaitingScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .safeDrawingPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            tint = black,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Start)
                .clickable { onBack() }
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = stringResource(R.string.taking_permission),
            fontSize = 16.sp,
            color = Color(0xFF888888)
        )
        Spacer(Modifier.weight(1f))
    }
}

@OptIn(ExperimentalGetImage::class)
@Composable
private fun CameraScreen(
    passportSeries: String,
    birthDate: String,
    uiState: KycCameraContract.UiState,
    onEventDispatcher: (KycCameraContract.Intent) -> Unit
) {
    val navigator = LocalNavigator.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var faceState by remember { mutableStateOf<FaceState>(FaceState.Scanning) }
    var photoTaken by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "ring")
    val ringAlpha by infiniteTransition.animateFloat(
        initialValue = 0.55f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(700, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "alpha"
    )

    val ringColor = when (faceState) {
        is FaceState.Detected -> enabled
        else -> redColor
    }
    val ringAlphaFinal = if (faceState is FaceState.Detected) ringAlpha else 1f

    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val faceDetector = remember {
        FaceDetection.getClient(
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setMinFaceSize(0.25f)
                .build()
        )
    }
    val previewView = remember {
        PreviewView(context).apply { scaleType = PreviewView.ScaleType.FILL_CENTER }
    }
    var lastAnalysisTime by remember { mutableStateOf(0L) }

    LaunchedEffect(Unit) {
        val providerFuture = ProcessCameraProvider.getInstance(context)
        providerFuture.addListener({
            val cameraProvider = providerFuture.get()

            val preview = Preview.Builder().build()
                .also { it.surfaceProvider = previewView.surfaceProvider }

            val imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            val analysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build().also { ia ->
                    ia.setAnalyzer(cameraExecutor) { proxy ->
                        val now = System.currentTimeMillis()
                        if (now - lastAnalysisTime < 350) {
                            proxy.close(); return@setAnalyzer
                        }
                        lastAnalysisTime = now

                        val mediaImage = proxy.image
                        if (mediaImage == null) {
                            proxy.close(); return@setAnalyzer
                        }

                        val input =
                            InputImage.fromMediaImage(mediaImage, proxy.imageInfo.rotationDegrees)
                        faceDetector.process(input)
                            .addOnSuccessListener { faces ->
                                if (faces.isNotEmpty()) {
                                    faceState = FaceState.Detected
                                    if (!photoTaken) {
                                        photoTaken = true
                                        val file = File(
                                            context.filesDir,
                                            "face_${
                                                SimpleDateFormat(
                                                    "yyyy.MM.dd_HH:mm:ss",
                                                    Locale.US
                                                ).format(Date())
                                            }.jpg"
                                        )
                                        imageCapture.takePicture(
                                            ImageCapture.OutputFileOptions.Builder(file).build(),
                                            cameraExecutor,
                                            object : ImageCapture.OnImageSavedCallback {
                                                @RequiresApi(Build.VERSION_CODES.O)
                                                override fun onImageSaved(out: ImageCapture.OutputFileResults) {
                                                    Log.d("TTT", "success")
                                                    Log.d("TTT", birthDate)
                                                    val birthDateFormatted =
                                                        birthDate.toApiDateFormat()
                                                    Log.d("TTT", birthDateFormatted)
                                                    onEventDispatcher(
                                                        KycCameraContract.Intent.OnSubmitKyc(
                                                            passportSeries.take(2),
                                                            passportSeries.takeLast(7),
                                                            birthDateFormatted,
                                                            imageToBase64(file.path)
                                                        )
                                                    )
                                                }

                                                override fun onError(e: ImageCaptureException) {
                                                    Log.e("TTT", "Failed", e)
                                                    photoTaken = false
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                            .addOnCompleteListener { proxy.close() }
                    }
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_FRONT_CAMERA,
                    preview, imageCapture, analysis
                )
            } catch (e: Exception) {
                Log.e("TTT", "Failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
            faceDetector.close()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .safeDrawingPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            tint = black,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Start)
                .clickable {
                    navigator?.pop()
                }
        )

        Spacer(Modifier.height(28.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.ic_xazna_logo),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Image(
                painter = painterResource(R.drawable.ic_xazna_name),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 14.dp, top = 16.dp)
                    .height(60.dp)
            )
            Spacer(Modifier.weight(1f))
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.make_face_inside),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = black,
            lineHeight = 22.sp
        )

        Spacer(Modifier.height(40.dp))

        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(300.dp)) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape)
            )
            Canvas(modifier = Modifier.size(300.dp)) {
                val strokePx = 6.dp.toPx()
                val radius = (size.minDimension - strokePx) / 2f
                drawCircle(
                    color = ringColor.copy(alpha = ringAlphaFinal),
                    radius = radius,
                    center = Offset(size.width / 2f, size.height / 2f),
                    style = Stroke(width = strokePx, cap = StrokeCap.Round)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Spacer(Modifier.weight(1f))

        Image(
            painter = painterResource(R.drawable.ic_my_id),
            contentDescription = null,
            modifier = Modifier.height(32.dp)
        )

        Spacer(Modifier.height(32.dp))
    }
    if (uiState.loading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(loadingTransparentBcg)
        ) {
            val imageLoader = ImageLoader.Builder(context)
                .components { add(GifDecoder.Factory()) }
                .build()
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.gif_loading)
                    .build(),
                contentDescription = null,
                imageLoader = imageLoader,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(84.dp)
            )
        }
    }
}