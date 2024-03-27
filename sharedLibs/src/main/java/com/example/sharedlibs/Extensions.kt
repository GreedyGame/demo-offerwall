package com.example.sharedlibs

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

fun Context.svgToBitmap(@DrawableRes drawable: Int): Bitmap? {
    return ContextCompat.getDrawable(
        this,
        drawable
    )?.toBitmap()
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}