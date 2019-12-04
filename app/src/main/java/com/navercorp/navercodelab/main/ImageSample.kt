package com.navercorp.navercodelab.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object ImageSample {

    fun getSeaImage(context: Context) :Bitmap? {
        return  context.assets.open("sea.jpeg").use {
            BitmapFactory.decodeStream(it)
        }

    }
}