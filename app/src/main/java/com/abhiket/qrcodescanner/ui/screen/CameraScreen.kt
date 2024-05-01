package com.abhiket.qrcodescanner.ui.screen

import android.os.Handler
import android.os.Looper
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.abhiket.qrcodescanner.ui.QrAnalyzer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.async
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException

/**
 * Here, we use the CameraX library to capture images,
 * which are then passed through an image proxy for analysis.
 * The analysis involves decoding QR codes.
 */
@Composable
fun CameraScreen(onQrCodeDecoded: (String) -> Unit) {

    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    val context = LocalContext.current
    val processCameraProvider = remember { ProcessCameraProvider.getInstance(context) }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val previewView = remember { PreviewView(context) }

    LaunchedEffect(lensFacing) {
        val size = Size(previewView.width, previewView.height)
        val strategy = ResolutionStrategy(size, ResolutionStrategy.FALLBACK_RULE_CLOSEST_LOWER)
        val selector = ResolutionSelector.Builder().setResolutionStrategy(strategy).build()
        val imageAnalysis = ImageAnalysis.Builder().setResolutionSelector(selector)
            .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST).build()
        // We pass the main thread handler to the Image Analyzer because
        // it will execute on a background thread.
        // Once the analysis is complete, the results will be posted back to the main thread.
        // Therefore, we require the main thread handler for updating of the user interface.
        imageAnalysis.setAnalyzer(Dispatchers.IO.asExecutor(),
            QrAnalyzer(Handler(Looper.getMainLooper())) { txt ->
                imageAnalysis.clearAnalyzer()
                onQrCodeDecoded(txt)
            })

        try {
            preview.setSurfaceProvider(previewView.surfaceProvider)
            // We use the async function to initialize the cameraProvide. So it will run on background thread"
            val cameraProvider = async(Dispatchers.IO) { processCameraProvider.get() }.await()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
        } catch (exp: IllegalStateException) {
            // If the use case has already been bound to another lifecycle
            // [OR] method is not called on main thread.
            exp.printStackTrace()
        } catch (exp: CancellationException) {
            // if the computation was cancelled */
            exp.printStackTrace()
        } catch (exp: ExecutionException) {
            // if the computation threw an exception */
            exp.printStackTrace()
        } catch (exp: InterruptedException) {
            exp.printStackTrace()
        }
    }
    AndroidView(modifier = Modifier.fillMaxSize(), factory = { previewView })
}