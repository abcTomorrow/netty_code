package com.wojiushiwo.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by myk
 * 2020/1/7 下午3:10
 * 使用ByteBuffer与FileChannel 将数据写入文件
 */
public class FileChannelWriteDemo {
    public static void main(String[] args) throws IOException {
        //使用这种方式不需要进行buffer读写转换
//        ByteBuffer byteBuffer=ByteBuffer.wrap("hello".getBytes());

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("hello".getBytes());

        //使用流来构造FileChannel
        FileOutputStream fos = new FileOutputStream("1.txt");
        FileChannel channel = fos.getChannel();

        byteBuffer.flip();
        channel.write(byteBuffer);

        channel.close();
        fos.close();

    }
}
