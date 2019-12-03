package com.navercorp.navercodelab.samples.coroutine.job

import kotlinx.coroutines.*

val job = SupervisorJob()
val scope = CoroutineScope(Dispatchers.Default + job)

fun loadData() = scope.launch {
    try {
        async(start = CoroutineStart.ATOMIC) {                                         // (1)
            // may throw Exception
        }.await()
    } catch (e: Exception) {

    }

}

fun cancelData() {
    scope.cancel();
}

//https://blog.kmshack.kr/kotlin_coroutine_pattern/