package com.abhiket.qrcodescanner.ui.navigation

sealed class Screen(val route: String) {
    data object CameraScreen : Screen("camera_screen")
    data object ResultScreen : Screen("result_screen")
}