package ru.mobile.utils

import android.graphics.*

fun Bitmap.resize(maxWidth: Int, maxHeight: Int): Bitmap {
    return try {
        Bitmap.createScaledBitmap(this, maxWidth, maxHeight, false)
    } catch (e: Exception) {
        this
    }
}

fun Bitmap.fill(maxLength: Int): Bitmap {
    return try {
        val aspectRatio = this.height.toDouble() / this.width.toDouble()
        val targetHeight = (maxLength * aspectRatio).toInt()
        Bitmap.createScaledBitmap(this, maxLength, targetHeight, false)
    } catch (e: Exception) {
        this
    }
}