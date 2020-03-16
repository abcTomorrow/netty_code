package com.wojiushiwo.nio;

import java.nio.ByteBuffer;

/**
 * Created by myk
 * 2020/1/7 下午4:06
 */
public class ByteBufferDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(40);
        //这里存放Double 需要8个字节 如果ByteBuffer空间小于8个字节 报错BufferOverflowException
        buffer.putDouble(0.1d);
        buffer.put((byte) 127);

        buffer.flip();

        /*
            ByteBuffer 支持类型化的 put 和 get, put 放入的是什么数据类型，
            get 就应该使用相应的数据类型来取出，否则可能有 BufferUnderflowException 异常
         */
        System.out.println(buffer.getDouble());
        System.out.println(buffer.get());
    }
}
