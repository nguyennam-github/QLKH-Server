/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hvan.qlkh.main;

import hvan.qlkh.thread.Client;
import hvan.qlkh.thread.ControlBus;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.xml.bind.JAXBException;

/**
 *
 * @author PC
 */
public class Server {

    public static Socket productsSocket;
    public static ServerSocket productsServer;
    public static Socket controlSocket;
    public static ServerSocket controlServer;
    
    public static void main(String[] args) throws IOException, JAXBException, ClassNotFoundException {
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
                controlSocket = server.accept();
                Client client = new Client(controlSocket, id++);
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