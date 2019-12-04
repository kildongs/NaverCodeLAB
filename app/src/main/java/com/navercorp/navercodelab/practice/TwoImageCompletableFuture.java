package com.navercorp.navercodelab.practice;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.widget.ImageView;

import com.example.background.workers.WorkerUtils;
import com.navercorp.navercodelab.main.ImageSample;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TwoImageCompletableFuture implements AbsTwoImage{

    public Activity activity;
    ImageView photoView;

    //ExecutorService executor = Executors.newFixedThreadPool(4);

    public TwoImageCompletableFuture(Activity activity, ImageView photoView) {
        this.activity = activity;
        this.photoView = photoView;
    }



    public void start()  {
        final CompletableFuture cf1= CompletableFuture.supplyAsync(()->{
            return WorkerUtils.blurBitmap(ImageSample.INSTANCE.getBGImage(activity), activity);

        },executor);

        CompletableFuture cf2= CompletableFuture.supplyAsync(()->{
            return ImageSample.INSTANCE.getTeaImage(activity);

        },executor);

        final CompletableFuture<Void> allFuture = CompletableFuture.allOf(cf1, cf2);

        allFuture.thenRun( ()-> {
            try {
                Bitmap bgBitmap = (Bitmap) cf1.get();
                Bitmap teaBitmap = (Bitmap) cf2.get();

                final Bitmap result = WorkerUtils.mergeImages(teaBitmap, bgBitmap, new Point(0,0));
                activity.runOnUiThread( () -> {
                    photoView.setImageBitmap(result);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

}
//https://m.blog.naver.com/2feelus/220714398973

//https://firstboos.tistory.com/entry/CompletableFuture-%EA%B8%B0%EB%8A%A5-%EC%82%B4%ED%8E%B4%EB%B3%B4%EA%B8%B0