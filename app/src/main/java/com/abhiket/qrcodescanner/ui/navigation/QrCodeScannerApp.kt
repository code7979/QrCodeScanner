package com.abhiket.qrcodescanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhiket.qrcodescanner.ui.screen.CameraScreen
import com.abhiket.qrcodescanner.ui.screen.ResultScreen
import com.abhiket.qrcodescanner.ui.viewmodels.ResultViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QrCodeScannerApp(
    viewModel: ResultViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.CameraScreen.route) {
        composable(Screen.CameraScreen.route) {
            CameraScreen {
                viewModel.setResultValue(it)
                navController.navigate(Screen.ResultScreen.route)
            }
        }

        composable(Screen.ResultScreen.route) {
            ResultScreen(viewModel.result) {
                navController.popBackStack()
            }
        }
    }
}