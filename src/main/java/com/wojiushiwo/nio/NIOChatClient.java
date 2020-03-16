package com.wojiushiwo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by myk
 * 2020/1/8 上午11:19
 * 无阻塞地发送消息给其他所有用户，同时可以接受其他用户发送的消息(服务器转发)
 */
public class NIOChatClient {

    private SocketChannel socketChannel;
    private Selector selector;
    private String userName;

    public NIOChatClient() throws Exception {
        socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6669));
        socketChannel.configureBlocking(false);

        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(userName + " is ok...");
    }

    public static void main(String[] args) throws Exception {
        NIOChatClient chatClient = new NIOChatClient();

        new Thread(() -> {
            try {
                while(true){
                    chatClient.readInfo();
                    Thread.sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClient.sendInfo(msg);
        }

    }

    public void sendInfo(String info) throws IOException {
        info = userName.concat("说").concat(info);
        socketChannel.write(ByteBuffer.wrap(info.getBytes()));
    }

    public void readInfo() throws IOException {

        while (true) {

            int select = selector.select();
            if (select > 0) {

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {

                    SelectionKey key = iterator.next();

                    if (key.isReadable()) {

                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int len = channel.read(buffer);
                        if (len > 0) {
                            String msg = new String(buffer.array(), 0, len);
                            System.out.println(msg.trim());
                        }

                    }

                    iterator.remove();

                }


            }

        }

    }
}
