/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hvan.qlkh.main;

import hvan.qlkh.services.Process;
import hvan.qlkh.services.ProcessBus;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

public class App {


    public static void main(String[] args) throws IOException{

        Socket socket;
        ServerSocket server = null;
        int id = 0;

        System.out.println("Server is waiting to accept user...");

        try {
            server = new ServerSocket(7777);
        } catch (IOException e) {
            System.exit(1);
        }

        try (ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                100,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8)
        )) {
            try {
                while (true) {
                    socket = server.accept();
                    Process client = new Process(socket, id++);
                    ProcessBus.getInstance().add(client);
                    System.out.println("Process with id=" + id + "is running");
                    executor.execute(client);
                }
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    server.close();
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}