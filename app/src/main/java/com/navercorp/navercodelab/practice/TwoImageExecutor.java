package com.navercorp.navercodelab.practice;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.widget.ImageView;

import com.example.background.workers.WorkerUtils;
import com.navercorp.navercodelab.main.ImageSample;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TwoImageExecutor {

    public TwoImageExecutor(Activity activity, ImageView photoView) {
        this.activity = activity;
        this.photoView = photoView;
    }
    public Activity activity;
    ImageView photoView;

    ExecutorService executor = Executors.newFixedThreadPool(4);

    Callable<Bitmap>  bgDecoder = ()-> {
        return WorkerUtils.blurBitmap(ImageSample.INSTANCE.getBGImage(activity), activity);
    } ;


    Callable<Bitmap> tagrgetDecoder = ()->{
        return ImageSample.INSTANCE.getTeaImage(activity);
    } ;

    public void start() {

        executor.execute(()-> {
            Future<Bitmap> bg = executor.submit(bgDecoder);
            Future<Bitmap> tea  = executor.submit(tagrgetDecoder);

            try {
                Bitmap bgBitmap = bg.get(1, TimeUnit.MINUTES);
                Bitmap teaBitmap = tea.get(1, TimeUnit.MINUTES);

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
