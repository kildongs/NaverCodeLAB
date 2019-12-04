package com.navercorp.navercodelab.threads

import android.graphics.*
import android.widget.ImageView
import com.navercorp.navercodelab.samples.coroutine.UI

import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread


class MultiFetchSample {

    lateinit var bgImageView:ImageView
    lateinit var profileImageView:ImageView


    fun loadProfileImage() : ByteArray? {
        runCatching {
            val file = File("/sdcard/profile.jpg")
            return FileInputStream(file).use {
                val buffer = ByteArray(it.available());
                it.read(buffer);
                buffer
            }
        }
        return null
    }

    fun loadProfileBG() : ByteArray? {
        runCatching {
            val file = File("/sdcard/profileBG.jpg")
            return FileInputStream(file).use {
                val buffer = ByteArray(it.available());
                it.read(buffer);
                buffer
            }
        }
        return null
    }

    fun loadProfileWithBG_Java() {
        //프로필 사진과 백그라운드

        val latch = CountDownLatch(2);
        val thread1:Thread = thread {
            loadProfileImage()?.let {
                BitmapFactory.decodeByteArray(it,0,it.size)
            }
            latch.countDown()
        }

        val thread2:Thread = thread {
            loadProfileBG()?.let {
                BitmapFactory.decodeByteArray(it,0,it.size)
            }
            latch.countDown()
        }

        latch.await()
    }


    fun loadProfileWithBG() {
        //프로필 사진과 백그라운드
        GlobalScope.launch(UI){
            val profileTask:Deferred<Bitmap?> = async{
                loadProfileImage()?.let {
                    BitmapFactory.decodeByteArray(it,0,it.size)
                }
            }

            val bgTask:Deferred<Bitmap?> = async{
                loadProfileBG()?.let {
                    BitmapFactory.decodeByteArray(it,0,it.size)
                }
            }

            val bgImage =  bgTask.await()
            if (bgImage != null) {
                bgImageView.setImageBitmap(bgImage)

                val proileImage  =  profileTask.await()
                profileImageView.setImageBitmap(proileImage)

            } else { //배경이 로드 되지 않으면 프로필 로드도 취소
                if (profileTask.isCompleted == false) {
                    profileTask.cancel()
                    //setDefaultImage()
                }
            }
        }
    }

    fun mergeAndSave(profileImage:Bitmap, profileBg:Bitmap, offset:Point) {
        val bitmap = mergeImages(profileImage, profileBg, offset);
        //bitmap.compress()
    }

    fun mergeImages(profileImage:Bitmap, profileBg:Bitmap, offset:Point):Bitmap {
        val bmOverlay = Bitmap.createBitmap(profileBg.getWidth(), profileBg.getHeight(), profileBg.getConfig())
        val canvas = Canvas(bmOverlay)
        canvas.drawBitmap(profileBg, Matrix(), null)
        canvas.drawBitmap(profileImage, offset.x.toFloat(), offset.y.toFloat(), null)
        return bmOverlay
    }


}
