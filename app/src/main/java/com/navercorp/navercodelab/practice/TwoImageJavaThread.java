package com.navercorp.navercodelab.practice;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.widget.ImageView;

import com.example.background.workers.WorkerUtils;
import com.navercorp.navercodelab.main.ImageSample;

public class TwoImageJavaThread {

    public TwoImageJavaThread(Activity activity, ImageView photoView) {
        this.activity = activity;
        this.photoView = photoView;
    }
    public Activity activity;
    ImageView photoView;


    Bitmap bgBitmp;
    Bitmap objBitmp;

    Runnable composite  = new Runnable() {
        @Override
        public void run() {
            Thread th1 = new Thread(()->{
                bgBitmp = WorkerUtils.blurBitmap(ImageSample.INSTANCE.getBGImage(activity), activity);
            });
            th1.start();

            Thread th2 = new Thread(()->{
                objBitmp = ImageSample.INSTANCE.getTeaImage(activity);
            });
            th2.start();

            try {
                th1.join();
                th2.join();
                final Bitmap result = WorkerUtils.mergeImages(objBitmp, bgBitmp, new Point(0,0));
                activity.runOnUiThread(()-> {
                    photoView.setImageBitmap(result);
                });
                //merge
            } catch (Exception e) {

            }

        }
    };

    public void start() {
        new Thread(composite).start();

    }

}
