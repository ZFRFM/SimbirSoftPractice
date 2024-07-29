package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.faimizufarov.simbirtraining.R

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    backPressed: () -> Unit,
) {
    Box {
        TopAppBar(
            colors =
                TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.leaf),
                ),
            title = { },
            navigationIcon = {
                IconButton(onClick = { backPressed.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.authorization),
                        tint = colorResource(id = R.color.white),
                    )
                }
            },
        )
        Text(
            modifier =
                modifier.align(Alignment.Center),
            text = stringResource(R.string.authorization),
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.officina_sans_extra_bold_c, FontWeight.ExtraBold)),
            fontSize = 21.sp,
            textAlign = TextAlign.Center,
        )
    }
}
