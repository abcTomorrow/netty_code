package com.wojiushiwo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by myk
 * 2020/1/23 下午3:08
 */
public class ByteBufDemo {
    public static void main(String[] args) {
        //分配一个容量为10的缓冲区
        ByteBuf byteBuf = Unpooled.buffer(10);
            //ByteBuf内部维护了一个byte数组、readerIndex、writerIndex、capacity
            //不需要进行读写切换，通过 readerIndex 和 writerIndex 和 capacity， 将 buffer 分成三个区域
            //0--readerIndex 已经读取的区域
            //readerIndex--writerIndex 可读取的区域
            //writerIndex--capacity 可写的区域

        for (int i = 0; i < 10; i++) {
            //往缓冲区写数据 writerIndex会+1
            byteBuf.writeByte(i);
        }
        for (int i = 0; i < 10; i++) {
            //readByte()方法 会使readerIndex+1
            System.out.println(byteBuf.readByte());
            System.out.println("===>" + byteBuf.readerIndex());
        }

    }
}
