package com.wojiushiwo.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.stream.Stream;

/**
 * Created by myk
 * 2020/1/7 下午7:46
 * 将数据写入buffer时，可以指定buffer数组，数据会自动依次写入到buffer数组的每个元素里
 */
public class ScatteringDemo {

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("1.txt");

        ByteBuffer[] buffers = new ByteBuffer[2];

        buffers[0]=ByteBuffer.allocate(4);
        buffers[1]=ByteBuffer.allocate(32);

        FileChannel channel = fis.getChannel();

        channel.read(buffers);


        Stream.of(buffers)
                .forEach(buffer -> buffer.flip());

        Stream.of(buffers).forEach(buffer -> {
            while (buffer.hasRemaining()) {
                System.out.println((char)buffer.get());
            }
        });


    }

}
