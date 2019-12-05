package com.navercorp.navercodelab.samples.coroutine.scope

import com.navercorp.navercodelab.samples.coroutine.IO
import com.navercorp.navercodelab.samples.coroutine.realtimeMedia
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ParentChildScope : CoroutineScope {
    val job = SupervisorJob()


    override val coroutineContext: CoroutineContext
        get() = CoroutineName("ParentScope") + job + realtimeMedia



    fun start() {
        async (coroutineContext) {

            val childScope:CoroutineScope = CoroutineScope(IO + Job(job))
            val childJob = Job(job)

            launch(childJob+ IO) {

            }


        }

    }

    fun stop() {

    }
}