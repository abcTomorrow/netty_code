package com.wojiushiwo.nio;

import java.nio.IntBuffer;

/**
 * Created by myk
 * 2020/1/7 下午2:38
 */
public class IntBufferDemo {
    public static void main(String[] args) {
        //实现类HeapIntBuffer
        IntBuffer buffer = IntBuffer.allocate(5);
        buffer.put(1);
        buffer.put(2);
        buffer.put(3);
        buffer.put(4);
        buffer.put(5);

        //写模式切换成读模式 position/limit 数据变化
        buffer.flip();

        while (buffer.hasRemaining()) {

            System.out.println(buffer.get());
        }
    }
}
