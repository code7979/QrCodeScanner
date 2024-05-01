package com.abhiket.qrcodescanner.ui.screen

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhiket.qrcodescanner.R
import com.abhiket.qrcodescanner.ui.theme.ColorBlack
import com.abhiket.qrcodescanner.ui.theme.ColorLiteGray
import com.abhiket.qrcodescanner.ui.theme.ColorWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    result: State<String>,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    val text by result
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_result_screen)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorLiteGray,
                    titleContentColor = ColorBlack,
                    navigationIconContentColor = ColorBlack,
                    actionIconContentColor = ColorBlack,
                    scrolledContainerColor = ColorWhite
                ),
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center,
                    text = text
                )
            }

            Box(
                modifier = Modifier
                    .background(ColorLiteGray)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        copyToClipBoard(context, copiedText = text)
                        Toast.makeText(
                            context,
                            context.getString(R.string.copied_to_clipboard),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_copy),
                            contentDescription = stringResource(id = R.string.copy_button)
                        )
                        Text(
                            text = stringResource(id = R.string.copy),
                            style = TextStyle(fontSize = 10.sp)
                        )
                    }

                }
            }

        }
    }
}

fun copyToClipBoard(context: Context, copiedText: String) {
    val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    // clip data is initialized with the text variable declared above
    var clipData: ClipData = ClipData.newPlainText("scanned_text", copiedText)
    // Clipboard saves this clip object
    clipboardManager.setPrimaryClip(clipData)
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen(result = mutableStateOf("Preview Screen")) {

    }
}