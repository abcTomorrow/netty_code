package com.wojiushiwo.nio;

import java.nio.ByteBuffer;

/**
 * Created by myk
 * 2020/1/7 下午4:12
 * 只读buffer Demo
 */
public class ReadBufferDemo {
    public static void main(String[] args) {
        //实际类是HeapByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);

        for (int i = 0; i < 16; i++) {
            buffer.put((byte) i);
        }

        buffer.flip();
        //只读缓冲区会共享当前缓存区内容，即便当前buffer内容改变了 readOnlyBuffer也会同步改变 包括pos/limit等
        //readOnlyBuffer 实现类实际是HeapByteBufferR
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
        //原始缓冲区发生变化则只读缓冲区对应数据也变化了
        buffer.put((byte) 16);

        readOnlyBuffer.flip();
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
        //这里会报错 只读缓冲区不能写
        readOnlyBuffer.put((byte) 17);

    }
}
