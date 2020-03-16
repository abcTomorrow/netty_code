package com.wojiushiwo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * Created by myk
 * 2020/1/23 下午3:20
 */
public class ByteBufDemo2 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world", CharsetUtil.UTF_8);

       // 当且仅当此缓冲区具有后备字节数组时，才返回true,才能调用array()和arrayOffset()
        if(byteBuf.hasArray()){
            byte[] array = byteBuf.array();
            System.out.println(new String(array,CharsetUtil.UTF_8).trim());
            //返回数组的偏移量 0
            System.out.println(byteBuf.arrayOffset());
            //读索引的位置 0
            System.out.println("读索引的位置："+byteBuf.readerIndex());
            //写索引的位置 11
            System.out.println("写索引的位置："+byteBuf.writerIndex());
            //缓冲区容量 33
            System.out.println("缓冲区容量:"+byteBuf.capacity());

            System.out.println((char) byteBuf.readByte());
            //这个方法不改变readerIndex/writerIndex
            System.out.println((char)byteBuf.getByte(0));
            //读索引的位置 1
            System.out.println("读索引的位置："+byteBuf.readerIndex());
            //可读的字节数 10
            System.out.println("可读的字节数:"+byteBuf.readableBytes());
            //从readIndex处开始 读取length个
            //结果 ello
            System.out.println("按照范围读取："+byteBuf.readCharSequence(4,CharsetUtil.UTF_8));
            //读索引的位置 5
            System.out.println("读索引的位置："+byteBuf.readerIndex());
            //可读的字节数 6
            System.out.println("可读的字节数:"+byteBuf.readableBytes());

        }
    }
}
