package uz.mobiler.gita.xaznabankingclone.presentation.screens.transferByRequisites

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.presenter.viewModels.transferToCardScreen.TransferToCardContract
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.appSettings.AppThemeOption
import uz.mobiler.gita.xaznabankingclone.ui.theme.XaznaBankingCloneTheme
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class TransferByRequisitesScreen : Screen {
    @Composable
    override fun Content() {
        TransferByRequisitesContent()
    }
}

@Composable
private fun TransferByRequisitesContent() {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val doNotWork = stringResource(R.string.do_not_work)
    var checkBoxState by remember { mutableStateOf(false) }
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var input3 by remember { mutableStateOf("") }
    var input4 by remember { mutableStateOf("") }

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
                    text = stringResource(R.string.transfer_by_requisites),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checkBoxState, onCheckedChange = {
                    checkBoxState = it
                })
                Text(
                    stringResource(R.string.payment_to_budget),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Text(
                stringResource(R.string.recipient_bank_code),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 18.dp, top = 12.dp)
            )
            BasicTextField(
                value = input1,
                onValueChange = {
                    input1 = it
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = white,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W600
                ),
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, top = 4.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onPrimaryFixed,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                    ) {

                        innerTextField()
                    }
                }
            )

            Text(
                stringResource(R.string.recipient_account),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 18.dp, top = 12.dp)
            )
            BasicTextField(
                value = input2,
                onValueChange = {
                    input2 = it
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = white,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W600
                ),
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, top = 4.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onPrimaryFixed,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                    ) {

                        innerTextField()
                    }
                }
            )

            Text(
                stringResource(R.string.recipient_name),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 18.dp, top = 12.dp)
            )
            BasicTextField(
                value = input3,
                onValueChange = {
                    input3 = it
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = white,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W600
                ),
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, top = 4.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onPrimaryFixed,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                    ) {

                        innerTextField()
                    }
                }
            )

            Text(
                stringResource(R.string.payment_name),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 18.dp, top = 12.dp)
            )
            BasicTextField(
                value = input4,
                onValueChange = {
                    input4 = it
                },
                textStyle = TextStyle(
                    color = white,
                    fontSize = 17.sp,
                    lineHeight = 1.em,
                    fontWeight = FontWeight.W600
                ),
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, top = 4.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onPrimaryFixed,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.height(100.dp)
                    ) {
                        if (input4.isEmpty()) {
                            Text(
                                stringResource(R.string.attention),
                                color = MaterialTheme.colorScheme.onPrimaryFixed,
                                lineHeight = 1.em,
                                fontSize = 17.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
        Text(
            stringResource(R.string.next).uppercase(),
            color = if (input1.isNotEmpty() && input2.isNotEmpty() && input3.isNotEmpty() && input4.isNotEmpty()) white else MaterialTheme.colorScheme.onPrimaryFixed,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(18.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(
                    input1.isNotEmpty() && input2.isNotEmpty() && input3.isNotEmpty() && input4.isNotEmpty(),
                    onClick = {
                        Toast.makeText(context, doNotWork, Toast.LENGTH_SHORT).show()
                    })
                .background(
                    if (input1.isNotEmpty() && input2.isNotEmpty() && input3.isNotEmpty() && input4.isNotEmpty()) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onBackground
                )
                .padding(11.dp)
        )
    }
}

@Preview
@Composable
private fun TransferByRequisitesContentPreview() {
    XaznaBankingCloneTheme(AppThemeOption.DARK) {
        TransferByRequisitesContent()
    }
}