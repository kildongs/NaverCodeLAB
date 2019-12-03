package com.navercorp.navercodelab.threads;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;


// http://tutorials.jenkov.com/java-io/pipes.html

public class AudioPipeStream {

     PipedOutputStream output = new PipedOutputStream();
    PipedInputStream  input;


    Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                output.write("Hello world, pipe!".getBytes());
            } catch (IOException e) {
            }
        }
    });


    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                int data = input.read();
                while(data != -1){
                    System.out.print((char) data);
                    data = input.read();
                }
            } catch (IOException e) {
            }
        }
    });

    void start() throws Exception {
        input  = new PipedInputStream(output);
        thread1.start();
        thread2.start();
    }



}
