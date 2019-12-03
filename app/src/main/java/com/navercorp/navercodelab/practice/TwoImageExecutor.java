package com.navercorp.navercodelab.practice;

import android.graphics.Bitmap;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TwoImageExecutor {

    ExecutorService executor = Executors.newFixedThreadPool(4);

    Callable<Bitmap>  bgDecoder = new Callable<Bitmap>() {
        @Override
        public Bitmap call() throws Exception {
            return null;
        }
    } ;


    Callable<Bitmap> tagrgetDecode = new Callable() {
        @Override
        public Object call() throws Exception {
            return null;
        }
    } ;

    Runnable composite  = new Runnable() {
        @Override
        public void run() {
            Future<Bitmap> imageF1 = executor.submit(tagrgetDecode);
            Future<Bitmap> imageF2 = executor.submit(tagrgetDecode);

            try {
                imageF1.get(1, TimeUnit.MINUTES);
                imageF2.get(1, TimeUnit.MINUTES);
            } catch (Exception e) {

            }


        }
    };

    void start() {


        executor.submit(composite);


    }

}
