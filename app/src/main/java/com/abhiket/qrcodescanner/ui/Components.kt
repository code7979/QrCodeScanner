package com.abhiket.qrcodescanner.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhiket.qrcodescanner.R

@Composable
fun CameraPermissionDenied(
    @StringRes title: Int,
    @StringRes description: Int,
    @StringRes buttonText: Int,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.ic_camera_illu), contentDescription = null)
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = stringResource(id = title),
            style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(text = stringResource(id = description))
        Spacer(modifier = Modifier.width(32.dp))
        FilledTonalButton(
            onClick = onButtonClick
        ) {
            Text(text = stringResource(id = buttonText))
        }

    }
}