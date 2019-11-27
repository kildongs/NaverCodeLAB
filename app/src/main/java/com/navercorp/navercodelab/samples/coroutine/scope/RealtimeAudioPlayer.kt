package com.navercorp.navercodelab.samples.coroutine.scope

import com.navercorp.navercodelab.samples.coroutine.realtimeMedia
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

class RealtimeAudioPlayer : CoroutineScope {
    val job = Job()


    override val coroutineContext: CoroutineContext
        get() = CoroutineName("RealtimeMedia") + job + realtimeMedia



    fun start() {
        async (coroutineContext) {

        }

    }

    fun stop() {

    }
}