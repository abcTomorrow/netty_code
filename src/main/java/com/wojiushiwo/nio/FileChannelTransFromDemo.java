package com.wojiushiwo.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by myk
 * 2020/1/7 下午4:00
 * 使用TransFrom拷贝文件
 */
public class FileChannelTransFromDemo {
    public static void main(String[] args) throws Exception {

        FileInputStream fis = new FileInputStream("1.txt");
        FileChannel readChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("3.txt");
        FileChannel writeChannel = fos.getChannel();

        //将文件1.txt拷贝到3.txt中
        long l = writeChannel.transferFrom(readChannel, 0, fis.available());
        System.out.println(l);
        writeChannel.close();
        fos.close();
        readChannel.close();
        fis.close();

    }
}
