package com.navercorp.navercodelab.samples.coroutine.dispatcher

import android.os.Process
import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import kotlin.coroutines.CoroutineContext

val processorCount : Int
    inline get() = Runtime.getRuntime().availableProcessors()

val customExecutor: Executor = Executors.newFixedThreadPool(
    processorCount,object : ThreadFactory {
        override fun newThread(r: Runnable?): Thread {
            return Thread(r).apply {
                priority = Process.THREAD_PRIORITY_URGENT_AUDIO
            }
        }
    }) //

val customDispatcher = object : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        customExecutor.execute(block)
    }
}

