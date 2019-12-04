package com.navercorp.navercodelab.practice;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class TwoImageJavaThread {

    public TwoImageJavaThread(Activity activity, ImageView photoView) {
        this.activity = activity;
        this.photoView = photoView;
    }
    public Activity activity;
    ImageView photoView;


    Runnable composite  = new Runnable() {
        @Override
        public void run() {
            Thread th1 = new Thread(()->{

            });
            th1.start();

            Thread th2 = new Thread(()->{

            });
            th2.start();

            try {
                th1.join();
                th2.join();

                //merge
            } catch (Exception e) {

            }

        }
    };

    void start() {


    }

}
