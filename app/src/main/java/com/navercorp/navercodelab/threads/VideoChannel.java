package com.navercorp.navercodelab.threads;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class VideoChannel {

    Pipe.SinkChannel sinkChannel = null;
    Pipe.SourceChannel sourceChannel = null;
    void start() {
        Pipe pipe = Pipe.open();

        sinkChannel = pipe.sink();
        sourceChannel = pipe.source();
    }


    void write() {
        String newData = "New String to write to file..." + System.currentTimeMillis();

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            sinkChannel.write(buf);
        }
    }

    void read() {
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = sourceChannel.read(buf);
    }
}
