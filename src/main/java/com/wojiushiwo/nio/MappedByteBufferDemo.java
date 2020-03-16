package com.wojiushiwo.nio;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by myk
 * 2020/1/7 下午4:27
 * MappedByteBuffer可以实现数据在堆外内存中直接修改 而不需要拷贝一次
 */
public class MappedByteBufferDemo {

    public static void main(String[] args) throws Exception{
        //使用MapMode.READ_WRITE 必须保证读取的文件是可读写的,否则抛出异常
//        FileInputStream fis=new FileInputStream("1.txt");
//        FileChannel channel = fis.getChannel();

        RandomAccessFile accessFile=new RandomAccessFile("1.txt","rw");
        FileChannel channel = accessFile.getChannel();
        /**
         * FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 0 : 可以直接修改的起始位置
         * 5: 是映射到内存的大小(不是索引位置) ,即将 1.txt 的多少个字节映射到内存
         * 可以直接修改的范围就是 0-5
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0,(byte)'A');

        channel.close();
        accessFile.close();


    }

}
