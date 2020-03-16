package com.wojiushiwo.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by myk
 * 2020/1/7 下午3:30
 * 使用buffer和channel 实现文件的拷贝。使用一个channel将文件数据读取到buffer中，
 * 再使用另一个channel从buffer中读取数据
 */
public class FileChannelCopyDemo {
    public static void main(String[] args) throws Exception {


        FileInputStream fis = new FileInputStream("1.txt");
        FileChannel readChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("2.txt");
        FileChannel writeChannel = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(2);

        while (true) {
            //循环读取、写入数据时 记得clear
            //否则 buffer中内容被读取之后position=limit，下面的read会读不到数据
            //read会变成0 造成在这里死循环
            buffer.clear();
            int read = readChannel.read(buffer);
            if (read == -1) {
                break;
            }
            buffer.flip();
            writeChannel.write(buffer);
        }

        writeChannel.close();
        fos.close();
        readChannel.close();
        fis.close();
    }
}
