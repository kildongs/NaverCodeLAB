package com.navercorp.navercodelab.practice

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

class TwoImageCoroutineSample : CoroutineScope{
    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = CoroutineName("TwoImage") + job


    suspend fun decode() {
        val image1 = async {


        }

        val image2 = async {

        }

        //image1.await() + image2.await()
    }

    fun start() {
        async {
            decode()
            //save
        }
    }
}