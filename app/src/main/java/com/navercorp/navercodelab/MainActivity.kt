package com.navercorp.navercodelab

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.background.workers.blurBitmap
import com.example.background.workers.writeBitmapToFile
import com.navercorp.navercodelab.samples.coroutine.Default
import com.navercorp.navercodelab.samples.coroutine.IO
import com.navercorp.navercodelab.samples.coroutine.UI
import com.navercorp.navercodelab.samples.coroutine.mergeImages
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


class MainActivity : AppCompatActivity() {


    val photos = arrayOf("aurora.jpg", "door.png", "matehorn.jpeg", "sea.jpeg", "tea.png", "window_1.png", "window_2.png")
    val targetName = "tea.png"
    val bgName = "matehorn.jpeg"
    lateinit var photoView: ImageView


    operator fun  Bitmap.plus(bg:Bitmap):Bitmap  = mergeImages( bg, this, Point(0,0))

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        photoView = findViewById(R.id.photoView)

    }


    override fun onDestroy() {
        super.onDestroy()

    }


}
