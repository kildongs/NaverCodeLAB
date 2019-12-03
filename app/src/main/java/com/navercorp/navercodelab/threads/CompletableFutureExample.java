package com.navercorp.navercodelab.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureExample {
    void safeSleep(long millis) {
        try{Thread.sleep(millis);} catch(Exception ex){};
    }

    void printLog(String log) {

    }


    void start() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture.runAsync(()->{

        }, executor)
                .thenRun(()-> {

                }

        );

    }



    void compose3() throws Exception {
        ExecutorService e = Executors.newCachedThreadPool();

        long startTime = System.currentTimeMillis();

        CompletableFuture cf1= CompletableFuture.supplyAsync(()->{
            safeSleep(500);
            System.out.println("cf1 supplyAsync on thread "+Thread.currentThread().getId()+" now="+(System.currentTimeMillis()-startTime));
            return 100;

        });

        CompletableFuture cf2= CompletableFuture.supplyAsync(()->{
            safeSleep(1000);
            System.out.println("cf2 supplyAsync on thread "+Thread.currentThread().getId()+" now="+(System.currentTimeMillis()-startTime));
            return 200;

        });

        CompletableFuture cf3= CompletableFuture.supplyAsync(() -> {
            safeSleep(3000);
            System.out.println("cf3 supplyAsync on thread "+Thread.currentThread().getId()+" now="+(System.currentTimeMillis()-startTime));
            return 300;

        },e);

        System.out.println("Task execution requested on thread " + Thread.currentThread().getId());

        cf3.thenComposeAsync((data1) -> cf2).thenComposeAsync( (data2)->cf1).join();



        System.out.println("final cf1.get() = " + cf1.get()+ " cf2.get()="+cf2.get()+" cf3.get()="+cf3.get()+" now="+(System.currentTimeMillis()-startTime));
    }

}
//https://m.blog.naver.com/2feelus/220714398973