package uz.mobiler.gita.xaznabankingclone.presentation.screens.profileDetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mobiler.gita.presenter.viewModels.homeScreen.HomeContract
import uz.mobiler.gita.presenter.viewModels.profileDetailScreen.ProfileDetailContract
import uz.mobiler.gita.presenter.viewModels.profileDetailScreen.ProfileDetailViewModel
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.presentation.screens.identification.IdentificationScreen
import uz.mobiler.gita.xaznabankingclone.presentation.screens.noConnectionScreen.NoConnectionScreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.darkGreen
import uz.mobiler.gita.xaznabankingclone.ui.theme.enabled
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.loadingTransparentBcg
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class ProfileDetailScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: ProfileDetailContract.ViewModel = hiltViewModel<ProfileDetailViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        viewModel.collectSideEffect {
            when (it) {
                is ProfileDetailContract.SideEffect.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is ProfileDetailContract.SideEffect.NoConnection -> {
                    navigator?.push(NoConnectionScreen())
                }

                is ProfileDetailContract.SideEffect.PopBack -> {
                    navigator?.pop()
                }
            }
        }
        ProfileDetailContent(
            uiState.value,
            viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun ProfileDetailContent(
    uiState: ProfileDetailContract.UiState,
    onEventDispatcher: (ProfileDetailContract.Intent) -> Unit
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val newUserString = stringResource(R.string.new_user)
    val pref = context.getSharedPreferences("register", Context.MODE_PRIVATE)
    val name = pref.getString("name", "") ?: ""
    val phoneNumber = pref.getString("phone_number", "") ?: ""
    var nameEdit by remember { mutableStateOf(name.ifEmpty { newUserString }) }
    var imageUri by remember { mutableStateOf(pref.getString("image_uri", null)) }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                imageUri = it.toString()
                pref.edit().putString("image_uri", it.toString()).apply()
            }
        }

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
                            if (name != nameEdit) {
                                onEventDispatcher(ProfileDetailContract.Intent.OnUpdateName(nameEdit))
                            } else {
                                navigator?.pop()
                            }
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
                    text = stringResource(R.string.my_info),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(76.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(
                                MaterialTheme.colorScheme.onPrimaryFixed,
                                RoundedCornerShape(100.dp)
                            )
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_profile),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(76.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(
                                MaterialTheme.colorScheme.onPrimaryFixed,
                                RoundedCornerShape(100.dp)
                            )
                    )
                }
                Image(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(36.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .clickable {
                            launcher.launch(arrayOf("image/*"))
                        }
                        .padding(8.dp)
                )
            }

            Spacer(Modifier.height(18.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "F.I.SH",
                    color = MaterialTheme.colorScheme.onPrimaryFixed,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )

                BasicTextField(
                    value = nameEdit,
                    onValueChange = { if (it.length <= 32) nameEdit = it },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600
                    ),
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(200.dp)
                )
            }
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.onPrimaryFixed
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.phone_number),
                    color = MaterialTheme.colorScheme.onPrimaryFixed,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )

                Text(
                    phoneNumber, color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(200.dp)
                )
            }
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.onPrimaryFixed
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.account_status),
                    color = MaterialTheme.colorScheme.onPrimaryFixed,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )

                Text(
                    if (name.isEmpty()) newUserString else "Doimiy foydalanuvchi",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(200.dp)
                )
            }

            if (uiState.showKycButton) {
                val splitString = stringResource(R.string.pass_identification).split(" ")

                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .padding(horizontal = 18.dp, vertical = 18.dp)
                        .fillMaxWidth()
                        .height(118.dp)
                        .clickable {
                            navigator?.push(IdentificationScreen())
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 34.dp)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(darkGreen)
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .width(270.dp)
                                .fillMaxHeight()
                        ) {
                            Spacer(Modifier.weight(1f))
                            Text(
                                splitString[0],
                                color = enabled,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                splitString[1],
                                color = white,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.weight(1f))
                        }
                        Image(
                            painter = painterResource(R.drawable.ic_next),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(lightWhite),
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(26.dp)
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.ic_shield_realistic),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp)
                            .width(88.dp)
                    )
                }
            }
        }
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

//@Preview
//@Composable
//private fun ProfileDetailContentPreview() {
//    XaznaBankingCloneTheme(AppThemeOption.LIGHT) {
//        ProfileDetailContent()
//    }
//}
