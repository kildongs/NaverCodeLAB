package com.navercorp.navercodelab.threads;

import android.graphics.Bitmap;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueExample {

    BlockingQueue<Bitmap> bitmapQueue = new LinkedBlockingQueue<Bitmap>();

    public BlockingQueueExample() {

    }

    Runnable decodeRunnable = ()-> {

        try {
            Bitmap bitmap = bitmapQueue.poll(50, TimeUnit.MILLISECONDS);
            if (bitmap != null) {
                Bitmap target = bitmapQueue.take();
            }
        } catch (Exception e) {

        }

    };


}
