package com.navercorp.navercodelab.practice

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class FlowExample {



    fun hotFlow() {
        (1..100).filter { it % 2 == 0 }
            .onEach { print(it) }
            .map { it * it}
            .forEach {
                print(it)
            }
    }

    fun coldFlow() {
        runBlocking {
            (1..100).asFlow()
                .onEach { print(it) }
                .map { it * it }
                .collect() {
                    print(it)
                }
        }
    }
}