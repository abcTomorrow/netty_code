package com.wojiushiwo.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.stream.Stream;

/**
 * Created by myk
 * 2020/1/7 下午8:03
 * 从buffer中读取数据，可以是buffer数组，会依次读取buffer数组中的元素
 */
public class GatheringDemo {
    public static void main(String[] args) throws Exception {

        FileOutputStream fos = new FileOutputStream("4.txt");

        FileChannel channel = fos.getChannel();

        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(4);
        buffers[1] = ByteBuffer.allocate(8);

        for (int i = 0; i < 4; i++) {
            buffers[0].put((byte) (65 + i));
        }
        for (int i = 0; i < 8; i++) {
            buffers[1].put((byte) (65 + i));
        }

        Stream.of(buffers).forEach(buffer->buffer.flip());

        channel.write(buffers);
        channel.close();
        fos.close();

    }
}
