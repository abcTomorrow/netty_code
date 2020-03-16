package com.wojiushiwo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WebClient {
    public static void main(String[] args) throws IOException {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("0.0.0.0", 8090));

            ByteBuffer writeBuffer = ByteBuffer.allocate(32);
            ByteBuffer readBuffer = ByteBuffer.allocate(32);

            writeBuffer.put("hello".getBytes());
            writeBuffer.flip();
            while (true) {
                writeBuffer.rewind();
                socketChannel.write(writeBuffer);
                readBuffer.clear();
                int len = socketChannel.read(readBuffer);
                if (len > 0) {
                    System.out.println(new String(readBuffer.array(), 0, len));
                }
//                readBuffer.flip();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}