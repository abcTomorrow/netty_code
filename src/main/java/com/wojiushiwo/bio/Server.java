package com.wojiushiwo.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by myk
 * 2020/1/6 下午2:19
 * 使用BIO模型编写一个服务器端，监听6666端口，当有客户端连接时，就启动一个线程与之通讯。
 */
public class Server {

    private static int PORT = 6666;

    public static void main(String[] args) throws IOException {

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(PORT);


        while (true) {
            Socket socket = serverSocket.accept();

            cachedThreadPool.submit(() -> {
                handler(socket);
            });

        }
    }

    private static void handler(Socket socket) {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            while (true) {
                int read = inputStream.read(bytes);
                if (read == -1) {
                    return;
                }
                System.out.println("线程信息:" + Thread.currentThread().getId() + "，名字:" + Thread.currentThread().getName());
                System.out.println(new String(bytes, 0, read));
            }
        } catch (IOException e) {
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }


}
