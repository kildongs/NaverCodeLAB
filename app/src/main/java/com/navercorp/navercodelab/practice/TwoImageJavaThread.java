package com.navercorp.navercodelab.practice;

import android.graphics.Bitmap;

public class TwoImageJavaThread {

    Bitmap bgBitmap = null;
    Bitmap objBitmap = null;

    Integer count = 0;

    Runnable bgDecoder = new Runnable() {
        @Override
        public void run() {
            ++count;

            count.notify();

        }
    };

    Runnable tagrgetDecode = new Runnable() {
        @Override
        public void run() {

            ++count;
            count.notify();
        }
    };

    Runnable composite  = new Runnable() {
        @Override
        public void run() {
            Thread th1 = new Thread(bgDecoder);
            th1.start();

            Thread th2 = new Thread(bgDecoder);
            th2.start();

            try {
                th1.join();
                th2.join();
            } catch (Exception e) {

            }




        }
    };

    void start() {


    }

}
