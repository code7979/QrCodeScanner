package com.abhiket.qrcodescanner.utils

import java.nio.ByteBuffer

fun ByteBuffer.toByteArray(): ByteArray {
    rewind() // Rewind the buffer to zero
    val data = ByteArray(remaining())
    get(data) // Copy the buffer into byte array
    return data
}