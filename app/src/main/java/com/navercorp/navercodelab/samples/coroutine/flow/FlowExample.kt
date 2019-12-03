package com.navercorp.navercodelab.samples.coroutine.flow

import kotlinx.coroutines.*

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


val dataFlow = flow {
    for (i in 1..10) {
        delay(10)
        emit(i)
    }
}

val handler = CoroutineExceptionHandler { value1, value2 ->
    println("value1=$value1, value2 = $value2")
}

val flowContext = CoroutineName("CHECKER1") +handler + Dispatchers.IO

fun main() = runBlocking {
    val result = GlobalScope.async(flowContext) {
        //Thread.currentThread().setPriority(3)
        dataFlow.map{ it*it }
            .onEach{ println("onEach $it " + Thread.currentThread().priority); if (it == 9) throw Exception("haha")}
            .collect { println(it)}
    }
    println("Async finsihed")
    result.join()
}