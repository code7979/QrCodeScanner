package com.abhiket.qrcodescanner.ui

import android.graphics.ImageFormat
import android.os.Handler
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.core.ImageProxy
import com.abhiket.qrcodescanner.utils.toByteArray
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer

/**
 * Image analysis plays a pivotal role in our project,
 * allowing us to extract valuable information from captured images.
 * This section outlines the techniques and processes involved in analyzing images.
 */
class QrAnalyzer(
    private val mainThreadHandler: Handler,
    private val onQrCodeDecoded: (String) -> Unit
) : Analyzer {
    private val reader = MultiFormatReader().apply {
        setHints(mapOf(DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)))
    }
    private val validImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888
    )

    override fun analyze(image: ImageProxy) {
        if (!validImageFormats.contains(image.format)) {
            image.close()
            return
        }

        val bytes = image.planes.first().buffer.toByteArray()
        val imgWidth = image.width
        val imgHeight = image.height
        val source = PlanarYUVLuminanceSource(
            bytes,
            imgWidth,
            imgHeight,
            FORM_START,
            FORM_START,
            imgWidth,
            imgHeight,
            false
        )

        val bitmap = BinaryBitmap(HybridBinarizer(source))
        try {
            val result = reader.decode(bitmap)
            mainThreadHandler.post { onQrCodeDecoded(result.text) }
        } catch (e: NotFoundException) {
            e.printStackTrace()
        } finally {
            image.close()
        }
    }

    companion object {
        private const val FORM_START = 0
    }
}