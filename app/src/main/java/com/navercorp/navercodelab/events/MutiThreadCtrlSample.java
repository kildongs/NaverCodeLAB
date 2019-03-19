package com.navercorp.navercodelab.events;

import android.util.Log;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



// https://www.baeldung.com/java-cyclicbarrier-countdownlatch
public class MutiThreadCtrlSample {


    void countDownlatch() {

        CountDownLatch countDownLatch = new CountDownLatch(7);
        ExecutorService es = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            es.execute(() -> {
                long prevValue = countDownLatch.getCount();
                countDownLatch.countDown();
                if (countDownLatch.getCount() != prevValue) {
                    //outputScraper.add("Count Updated");
                }
            });
        }
        es.shutdown();
    }

    void cyclicBarrir() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7);

        ExecutorService es = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            es.execute(() -> {
                try {
                    if (cyclicBarrier.getNumberWaiting() <= 0) {
                        Log.d("ABC", "Count Updated");
                    }
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    // error handling
                }
            });
        }
        es.shutdown();
    }
}
