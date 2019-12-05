package com.navercorp.navercodelab.practice

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.widget.ImageView
import com.example.background.workers.blurBitmap
import com.example.background.workers.writeBitmapToFile
import com.navercorp.navercodelab.samples.coroutine.Default
import com.navercorp.navercodelab.samples.coroutine.IO
import com.navercorp.navercodelab.samples.coroutine.UI
import com.navercorp.navercodelab.samples.coroutine.mergeImages
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TwoImageCoroutineSample(
    val activity: Activity,
    val photoView: ImageView) : CoroutineScope{

    val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = CoroutineName("TwoImage") + job + IO

    operator fun  Bitmap.plus(bg:Bitmap):Bitmap  = mergeImages( bg, this, Point(0,0))

    fun start() {

        launch {
            val deferredBG = async  {
                val imageData = activity.assets.open("matehorn.jpeg").use {
                    BitmapFactory.decodeStream(it)
                }
                blurBitmap(imageData, activity)
            }


            val deferredTarget = async  {
                val imageData = activity.assets.open("tea.png").use {
                    BitmapFactory.decodeStream(it)
                }
                imageData
            }

            val deferredMerge = deferredBG.await() + deferredTarget.await()


            launch (UI){
                val resultImage = deferredMerge
                photoView.setImageBitmap(resultImage)
                launch(IO) {
                    writeBitmapToFile(activity, resultImage)
                }
            }
        }
    }

    fun finish() {
        job.cancel()
    }
}