package com.navercorp.navercodelab.practice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.CountDownLatch;

public class TwoImageLatchSample {


    ImageView bgImageView;
    ImageView profileImageView;


    byte[] loadProfileImage()  {
        try {
            File file = new File("/sdcard/profile.jpg");
            FileInputStream fio = new FileInputStream(file);

            byte[] buffer = new byte[fio.available()];
            fio.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    byte[] loadProfileBG()  {
        try {
            File file = new File("/sdcard/profileBG.jpg");
            FileInputStream fio = new FileInputStream(file);

            byte[] buffer = new byte[fio.available()];
            fio.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    Object sync = new Object();

    Bitmap bgBitmap;
    Bitmap profileBitmap;

    void test() throws Exception {

        CountDownLatch completeLatch = new CountDownLatch(2);

        Thread bgTask = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buffer = loadProfileBG();
                bgBitmap = BitmapFactory.decodeByteArray(buffer,0,buffer.length);

                completeLatch.countDown();
            }
        });

        Thread profileTask = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buffer = loadProfileImage();
                profileBitmap = BitmapFactory.decodeByteArray(buffer,0,buffer.length);
                completeLatch.countDown();
            }
        });

        bgTask.start();
        profileTask.start();

        completeLatch.await();

        if (bgBitmap == null) {
            //setDefaultImage()
            return;
        }

        bgImageView.setImageBitmap(bgBitmap);

        profileImageView.setImageBitmap(profileBitmap);
        //sync.wait();



    }
}
