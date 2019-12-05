package com.navercorp.navercodelab.practice

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.navercorp.navercodelab.gif.AnimatedGifEncoder
import com.navercorp.navercodelab.main.ImageSample
import com.navercorp.navercodelab.samples.coroutine.UI
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.io.ByteArrayOutputStream

class GifImageMaker(var activity: Activity, var photoView: ImageView) {
    fun start() = GlobalScope.launch {
        val bos = ByteArrayOutputStream()

        val encoder = AnimatedGifEncoder().apply {
            setDelay(100) // ディレイ 500/ms
            setRepeat(0) //
            start(bos) //
        }

        ImageSample.photos.map { decodeImage(it) }.awaitAll()
            .asFlow().map { resizeBitmap(it) }.collect {
                encoder.addFrame(it)
            }

        withContext(UI) {
            bos.toByteArray().let {
                photoView.setImageBitmap( BitmapFactory.decodeByteArray(it, 0, it.size))
            }
        }

        encoder.finish()
    }

    fun finish() {}


    fun decodeImage(photo:String) = GlobalScope.async {
        activity.assets.open(photo).use {
            BitmapFactory.decodeStream(it)
        }
    }

    fun resizeBitmap(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, 640 shr 1, 480 shr 1, true)
    }


}