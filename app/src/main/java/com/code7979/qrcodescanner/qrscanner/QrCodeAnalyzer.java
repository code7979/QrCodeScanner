package com.code7979.qrcodescanner.qrscanner;

import android.graphics.ImageFormat;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.nio.ByteBuffer;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class QrCodeAnalyzer implements ImageAnalysis.Analyzer {
    private Callback callback;

    public QrCodeAnalyzer(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onQrCodeScanned(String result);
    }

    private final List<Integer> supportedImageFormats = List.of(
            ImageFormat.YUV_420_888,
            ImageFormat.YUV_422_888,
            ImageFormat.YUV_444_888
    );

    @Override
    public void analyze(@NonNull ImageProxy image) {
        if (supportedImageFormats.contains(image.getFormat())) {
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = toByteArray(buffer);

            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
                    bytes,
                    image.getWidth(),
                    image.getHeight(),
                    0,
                    0,
                    image.getWidth(),
                    image.getHeight(),
                    false
            );

            BinaryBitmap binaryBmp = new BinaryBitmap(new HybridBinarizer(source));
            try (image) {
                Result result = new MultiFormatReader().decode(binaryBmp);
                callback.onQrCodeScanned(result.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] toByteArray(ByteBuffer byteBuffer) {
        byteBuffer.rewind();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return bytes;
    }
}
