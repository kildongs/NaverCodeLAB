package com.navercorp.navercodelab.samples.coroutine.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import java.nio.Buffer
import java.nio.ByteBuffer

class ChannelExample {

    val imageProcessingJob = Job()

    val sourceChannel = Channel<Buffer>(Channel.UNLIMITED)

    val context = CoroutineScope(imageProcessingJob )

    fun createChannels() {
        runBlocking{
            async(imageProcessingJob) {

                while (imageProcessingJob.isActive == true) {
                    val frame = sourceChannel.receive()

                }

            }
        }

    }

    fun onFrame(frame : ByteBuffer) {
        runBlocking(imageProcessingJob){
            sourceChannel.send(frame);
        }
    }
}