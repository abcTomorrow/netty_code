package com.wojiushiwo.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by myk
 * 2020/1/7 下午3:17
 */
public class FileChannelReadDemo {

    public static void main(String[] args) throws Exception {
        //从文件中读取内容 输出出来
        FileInputStream fis = new FileInputStream("1.txt");

        FileChannel channel = fis.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(fis.available());
        //返回读取的字节数，可能为0(position与limit相等时).如果当前channel已经读取到流的末尾 则返回-1
        int read = channel.read(buffer);
//        buffer.flip();

        //buffer.array()直接获取了buffer内部的数组 不需要读写转换，如果采用原来遍历的方式
        //需要buffer.flip() 否则报错BufferUnderflowException
        System.out.println(new String(buffer.array()).trim());
        channel.close();
        fis.close();
    }

}
