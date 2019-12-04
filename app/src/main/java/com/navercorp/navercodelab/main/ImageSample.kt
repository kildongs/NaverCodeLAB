package com.navercorp.navercodelab.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

object ImageSample {

    fun getBGImage(context: Context) :Bitmap? {
        return  context.assets.open("matehorn.jpeg").use {
            BitmapFactory.decodeStream(it)
        }

    }

    fun getTeaImage(context: Context) :Bitmap? {
        return  context.assets.open("tea.png").use {
            BitmapFactory.decodeStream(it)
        }

    }


    val photos = arrayOf("aurora.jpg", "door.png", "matehorn.jpeg", "sea.jpeg", "tea.png", "window_1.png", "window_2.png")
    fun decodeAll(context: Context) {
        measureTimeMillis {
            runBlocking {

                delay(1000)
                val result = GlobalScope.launch {
                    val jobs = photos.map {
                        async {
                            context.assets.open(it).use {
                                BitmapFactory.decodeStream(it)
                            }
                        }
                    }
                    jobs.awaitAll()

                }
            }

        }//.also { Toast.makeText(applicationContext, "elapsed = $it", Toast.LENGTH_LONG).show() }

    }

}