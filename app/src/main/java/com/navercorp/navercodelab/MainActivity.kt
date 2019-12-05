package com.navercorp.navercodelab

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.background.workers.blurBitmap
import com.example.background.workers.writeBitmapToFile
import com.navercorp.navercodelab.practice.*
import com.navercorp.navercodelab.samples.coroutine.Default
import com.navercorp.navercodelab.samples.coroutine.IO
import com.navercorp.navercodelab.samples.coroutine.UI
import com.navercorp.navercodelab.samples.coroutine.mergeImages
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.ArithmeticException
import java.lang.Exception
import kotlin.system.measureTimeMillis


class MainActivity : AppCompatActivity() {



    lateinit var photoView: ImageView




    operator fun  Bitmap.plus(bg:Bitmap):Bitmap  = mergeImages( bg, this, Point(0,0))

   // lateinit var twoImageCoroutine:TwoImageCoroutineSample

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoView = findViewById(R.id.photoView)

        //GifImageMaker(this,photoView).start()

        decode()

        //TwoImageJavaThread(this, photoView).start()
        //TwoImageExecutor(this, photoView).start()
        //TwoImageCompletableFuture(this, photoView).start();

//        twoImageCoroutine = TwoImageCoroutineSample(this, photoView)
//        twoImageCoroutine.start()


    }


    override fun onStart() {
        super.onStart()

        println("onSTart!!!")
    }

    override fun onStop() {
        super.onStop()
        println("onStop")

    }

    val handler = CoroutineExceptionHandler { context, th->
        print("$context ${th.toString()} ")
    }

    fun decode() {



        GlobalScope.launch {
            val deferredBG = async(IO)  {
                val imageData = assets.open("matehorn.jpeg").use {
                    BitmapFactory.decodeStream(it)
                }
                blurBitmap(imageData, this@MainActivity)
            }


            val deferredTarget = async(IO)  {
                val imageData =
                    assets.open("tea.png").use {
                        BitmapFactory.decodeStream(it)
                    }


                imageData
            }

            val deferredMerge:Bitmap? = try {
                deferredBG.await() + deferredTarget.await()
            } catch (e:Exception) {
                null
            }


            deferredMerge ?: return@launch

            val result:Uri = withContext (UI){
                val resultImage = deferredMerge
                photoView.setImageBitmap(resultImage)
                println("AAAAAA")
                val uri:Uri = withContext(IO) {
                    println("BBBBB")
                    delay(300)
                    writeBitmapToFile(this@MainActivity, resultImage)

                }
                println("CCC")

                uri
            }
            println("saved path = ${result.toString()}")
        }


    }




    override fun onDestroy() {
        super.onDestroy()

       // twoImageCoroutine.finish()

    }


    var count = 0;

    fun setWhenStart() {
        lifecycle.coroutineScope.launchWhenStarted {
            while ( 100 > count++) {

                println("Count = ${count++}")
                delay(100)
            }

        }

        lifecycleScope.launchWhenStarted {

            coroutineScope {

            }
        }
    }
}
