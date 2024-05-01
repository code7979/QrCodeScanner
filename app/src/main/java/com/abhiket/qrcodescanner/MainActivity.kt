package com.abhiket.qrcodescanner

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.abhiket.qrcodescanner.ui.CameraPermissionDenied
import com.abhiket.qrcodescanner.ui.navigation.QrCodeScannerApp
import com.abhiket.qrcodescanner.ui.theme.QrCodeScannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QrCodeScannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    var cameraPermissionStatus by remember {
                        mutableStateOf(getCameraPermissionStatus())
                    }

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = {
                            Log.d(TAG, "launcher: $it")

                            cameraPermissionStatus = if (it) {
                                PermissionState.GRANTED
                            } else {
                                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                                    PermissionState.RATIONALE
                                } else {
                                    PermissionState.PERMANENT
                                }
                            }
                        }
                    )

                    when (cameraPermissionStatus) {
                        PermissionState.GRANTED -> {
                            QrCodeScannerApp()
                        }

                        PermissionState.RATIONALE -> {
                            CameraPermissionDenied(
                                title = R.string.camera_access,
                                description = R.string.should_show_message,
                                buttonText = R.string.allow
                            ) {
                                launcher.launch(android.Manifest.permission.CAMERA)
                            }
                        }

                        PermissionState.DENIED -> {
                            LaunchedEffect(Unit) {
                                launcher.launch(android.Manifest.permission.CAMERA)
                            }
                        }

                        PermissionState.PERMANENT -> {
                            CameraPermissionDenied(
                                title = R.string.camera_access,
                                description = R.string.camera_permission_denied,
                                buttonText = R.string.go_to_app_setting
                            ) {
                                startAppSetting()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startAppSetting() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also(::startActivity)
    }

    private fun getCameraPermissionStatus(): PermissionState {
        return if (checkSelfPermission(
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            PermissionState.GRANTED
        } else {
            PermissionState.DENIED
        }
    }


    companion object {
        private const val TAG = "PermissionStatus"
    }

}