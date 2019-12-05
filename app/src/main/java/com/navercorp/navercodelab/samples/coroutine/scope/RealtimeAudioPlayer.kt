package com.navercorp.navercodelab.samples.coroutine.scope

import com.navercorp.navercodelab.samples.coroutine.IO
import com.navercorp.navercodelab.samples.coroutine.realtimeMedia
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RealtimeAudioPlayer : CoroutineScope {
    val job = SupervisorJob()


    override val coroutineContext: CoroutineContext
        get() = CoroutineName("RealtimeMedia") + job + realtimeMedia



    fun start() {
        async (coroutineContext) {

            launch(IO+Job(job)) {


            }

        }

    }

    fun stop() {

    }
}