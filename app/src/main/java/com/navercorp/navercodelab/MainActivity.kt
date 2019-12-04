package com.navercorp.navercodelab

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.background.workers.blurBitmap
import com.example.background.workers.writeBitmapToFile
import com.navercorp.navercodelab.practice.TwoImageCompletableFuture
import com.navercorp.navercodelab.practice.TwoImageCoroutineSample
import com.navercorp.navercodelab.practice.TwoImageExecutor
import com.navercorp.navercodelab.samples.coroutine.Default
import com.navercorp.navercodelab.samples.coroutine.IO
import com.navercorp.navercodelab.samples.coroutine.UI
import com.navercorp.navercodelab.samples.coroutine.dispatcher.CustomDispatcherExample
import com.navercorp.navercodelab.samples.coroutine.dispatcher.customDispatcher
import com.navercorp.navercodelab.samples.coroutine.dispatcher.reportDispatcher
import com.navercorp.navercodelab.samples.coroutine.mergeImages
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


val currrenThreadName : String
inline get() = "getCurrentThread = ${Thread.currentThread().name} priority = ${Thread.currentThread().priority}"

class MainActivity : AppCompatActivity() {



    lateinit var photoView: ImageView


    operator fun  Bitmap.plus(bg:Bitmap):Bitmap  = mergeImages( bg, this, Point(0,0))

   // lateinit var twoImageCoroutine:TwoImageCoroutineSample

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoView = findViewById(R.id.photoView)


        CustomDispatcherExample().showCoroutine()

        //decode()

        //TwoImageExecutor(this, photoView).start()
        //TwoImageCompletableFuture(this, photoView).start();

//        twoImageCoroutine = TwoImageCoroutineSample(this, photoView)
//        twoImageCoroutine.start()

    }

    fun decode() {
        GlobalScope.launch(Default) {
            val deferredBG = async<Bitmap>(IO)  {
                val imageData = assets.open("matehorn.jpeg").use {
                    BitmapFactory.decodeStream(it)
                }
                blurBitmap(imageData, this@MainActivity)
            }


            val deferredTarget = async<Bitmap>(IO)  {
                val imageData = assets.open("tea.png").use {
                    BitmapFactory.decodeStream(it)
                }
                imageData
            }

            val deferredMerge = deferredBG.await() + deferredTarget.await()


            launch (UI){
                val resultImage = deferredMerge
                photoView.setImageBitmap(resultImage)
                launch(IO) {
                    writeBitmapToFile(this@MainActivity, resultImage)
                }
            }
        }

    }






    override fun onDestroy() {
        super.onDestroy()

       // twoImageCoroutine.finish()

    }


}
