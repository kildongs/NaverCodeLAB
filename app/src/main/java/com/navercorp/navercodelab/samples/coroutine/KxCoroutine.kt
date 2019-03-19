package com.navercorp.navercodelab.samples.coroutine

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Point
import kotlinx.coroutines.Dispatchers

val IO = Dispatchers.IO
val UI = Dispatchers.Main
val Default = Dispatchers.Default

//

fun mergeAndSave(profileImage: Bitmap, profileBg: Bitmap, offset: Point) {
    val bitmap = mergeImages(profileImage, profileBg, offset);
    //bitmap.compress()
}

fun mergeImages(profileImage: Bitmap, profileBg: Bitmap, offset: Point): Bitmap {
    val bmOverlay = Bitmap.createBitmap(profileBg.getWidth(), profileBg.getHeight(), profileBg.getConfig())
    val canvas = Canvas(bmOverlay)
    canvas.drawBitmap(profileBg, Matrix(), null)
    canvas.drawBitmap(profileImage, offset.x.toFloat(), offset.y.toFloat(), null)
    return bmOverlay
}

