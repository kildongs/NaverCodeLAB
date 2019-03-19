package com.navercorp.navercodelab.events

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
import com.navercorp.navercodelab.R
import com.navercorp.navercodelab.samples.coroutine.Default
import com.navercorp.navercodelab.samples.coroutine.IO
import com.navercorp.navercodelab.samples.coroutine.UI
import com.navercorp.navercodelab.samples.coroutine.mergeImages
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


class MainActivity2 : AppCompatActivity() {


    val photos = arrayOf("aurora.jpg", "door.png", "matehorn.jpeg", "sea.jpeg", "tea.png", "window_1.png", "window_2.png")
    lateinit var photoView: ImageView


    operator fun  Bitmap.plus(bg:Bitmap):Bitmap  = mergeImages( bg, this, Point(0,0))


    fun decodeAll() {
        measureTimeMillis {
            runBlocking {

                delay(1000)
                val result = GlobalScope.launch {
                    val jobs = photos.map {
                        async {
                            assets.open(it).use {
                                BitmapFactory.decodeStream(it)
                            }
                        }
                    }
                    jobs.awaitAll()

                }
            }

        }.also { Toast.makeText(applicationContext, "elapsed = $it", Toast.LENGTH_LONG).show() }

    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoView = findViewById(R.id.photoView)


//        decodeAll()

            GlobalScope.launch(Default) {
                val imageData = assets.open("sea.jpeg").use {
                    BitmapFactory.decodeStream(it)
                }

                val result = blurBitmap(imageData, this@MainActivity2)

                //photoView.background = BitmapDrawable(deferredBG.await())
                //photoView.setImageBitmap(deferredTarget.await())


                    val deferredBG = async<Bitmap>(Default)  {
                        val imageData = assets.open("matehorn.jpeg").use {
                            BitmapFactory.decodeStream(it)
                        }
                        blurBitmap(imageData, this@MainActivity2)
                    }


                    val deferredTarget = async<Bitmap>(Default)  {
                        val imageData = assets.open("tea.png").use {
                            BitmapFactory.decodeStream(it)
                        }
                        imageData
                    }

               // awaitAll(deferredBG, deferredTarget)
                val deferredMerge = deferredBG.await() + deferredTarget.await()


                launch (UI){
                    val resultImage = deferredMerge
                    photoView.setImageBitmap(resultImage)
                    launch(IO) {
                        writeBitmapToFile(this@MainActivity2, resultImage)
                    }
                }
            }

    }


    override fun onDestroy() {
        super.onDestroy()

    }


    fun sampleLaunch() {


        runBlocking(IO) {

        }
    }

    fun sampleAsync() {

    }

    /*
    suspend fun decodeBG():Bitmap {

        return
    }*/

}
