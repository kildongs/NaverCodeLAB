package com.navercorp.navercodelab.samples.coroutine.dispatcher

import android.os.Process
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import kotlin.coroutines.CoroutineContext

val processorCount : Int
    inline get() = Runtime.getRuntime().availableProcessors()


val customExecutor: Executor = Executors.newCachedThreadPool(
    object : ThreadFactory {
        override fun newThread(r: Runnable?): Thread {
            return Thread(r, "Audio").apply {
                priority = Thread.MIN_PRIORITY
            }
        }
    })


val customDispatcher = object : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        customExecutor.execute(block)
    }
}



val reportExecutor: Executor = Executors.newFixedThreadPool(processorCount ,
    { runnable-> Thread(runnable,"Report" ).apply { priority = Thread.MAX_PRIORITY }}) //

val reportDispatcher = reportExecutor.asCoroutineDispatcher()

