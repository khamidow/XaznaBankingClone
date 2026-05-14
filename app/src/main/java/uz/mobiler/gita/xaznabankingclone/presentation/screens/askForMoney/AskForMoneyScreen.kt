package uz.mobiler.gita.xaznabankingclone.presentation.screens.askForMoney

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
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import uz.mobiler.gita.xaznabankingclone.R
import uz.mobiler.gita.xaznabankingclone.ui.theme.lightWhite
import uz.mobiler.gita.xaznabankingclone.ui.theme.white

class AskForMoneyScreen : Screen {
    @Composable
    override fun Content() {
        AskForMoneyContent()
    }
}

@Composable
private fun AskForMoneyContent() {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val doNotWork = stringResource(R.string.do_not_work)
    var input by remember { mutableStateOf("") }

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
                    Image(
                        painter = painterResource(R.drawable.ic_history),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterEnd),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed)
                    )
                }

                Text(
                    text = stringResource(R.string.request_for_funds),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, top = 6.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryFixed,
                        RoundedCornerShape(12.dp)
                    )
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = input,
                    onValueChange = {
                        input = it
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = white,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600
                    ),
                    modifier = Modifier
                        .weight(1f),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                        ) {
                            if (input.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search),
                                    color = lightWhite,
                                    fontSize = 17.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                Image(
                    painter = painterResource(R.drawable.ic_contact),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier.size(24.dp)
                )
            }

        }
    }
}

@Preview
@Composable
private fun AskForMoneyContentPreview() {
    AskForMoneyContent()
}