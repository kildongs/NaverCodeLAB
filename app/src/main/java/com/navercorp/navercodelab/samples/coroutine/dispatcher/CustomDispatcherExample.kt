package com.navercorp.navercodelab.samples.coroutine.dispatcher

import android.util.Log
import com.navercorp.navercodelab.currrenThreadName
import com.navercorp.navercodelab.samples.coroutine.IO
import kotlinx.coroutines.*

class CustomDispatcherExample {
    val job = Job();
    val reporterScope = CoroutineScope(CoroutineName("Reporter") + job+ reportDispatcher)

    fun showCoroutine() {
        reporterScope.launch {
            Log.e("Coroutine",  "thread1 = $currrenThreadName ${coroutineContext.toString()}"  )

            launch {
                Log.e("Coroutine","thread2-1 = $currrenThreadName  ${coroutineContext.toString()}")
            }
            launch(IO) {
                delay(1000);
                Log.e("Coroutine","thread2-2 = $currrenThreadName  ${coroutineContext.toString()}")

            }
        }

        runBlocking {
            delay(500)
            job.cancel()
        }
    }
}