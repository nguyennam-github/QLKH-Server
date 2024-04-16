/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author PC
 */
public class Server {

    public static Socket socket;

    public static void main(String[] args) {
        ServerSocket server = null;
        System.out.println("Server is waiting to accept user...");
        int id = 0;

        // Mở một ServerSocket tại cổng 7777.
        // Chú ý bạn không thể chọn cổng nhỏ hơn 1023 nếu không là người dùng
        // đặc quyền (privileged users (root)).
        try {
            server = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10, // corePoolSize
                100, // maximumPoolSize
                10, // thread timeout
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8) // queueCapacity
        );
        try {
            while (true) {
                // Chấp nhận một yêu cầu kết nối từ phía Client.
                // Đồng thời nhận được một đối tượng Socket tại server.
                socket = server.accept();
                Client client = new Client(socket, id++);
                ControlBus.getInstance().add(client);
                System.out.println("Số thread đang chạy là: " + ControlBus.getInstance().getLength());
                executor.execute(client);
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}