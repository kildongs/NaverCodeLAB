package com.navercorp.navercodelab.practice;

import android.graphics.Bitmap;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwoImageCompletableFuture implements AbsTwoImage{
    void safeSleep(long millis) {
        try{Thread.sleep(millis);} catch(Exception ex){};
    }

    void printLog(String log) {

    }




    void start() throws Exception {
        ExecutorService e = Executors.newCachedThreadPool();


        final CompletableFuture cf1= CompletableFuture.supplyAsync(()->{
            Bitmap image = null;
            return image;

        },e);

        CompletableFuture cf2= CompletableFuture.supplyAsync(()->{
            Bitmap image = null;
            return image;

        },e);

        final CompletableFuture<Void> allFuture = CompletableFuture.allOf(cf1, cf2);

        allFuture.thenAccept((v)->{
               // cf1.get(),cf2.get()

        });





    }

}
//https://m.blog.naver.com/2feelus/220714398973

//https://firstboos.tistory.com/entry/CompletableFuture-%EA%B8%B0%EB%8A%A5-%EC%82%B4%ED%8E%B4%EB%B3%B4%EA%B8%B0