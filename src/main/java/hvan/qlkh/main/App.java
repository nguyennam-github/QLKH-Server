/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hvan.qlkh.main;

import hvan.qlkh.services.Process;
import hvan.qlkh.services.ProcessBus;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

public class App {


    public static void main(String[] args){

        Socket socket;
        int port = 3000;
        ServerSocket server = null;
        int id = 0;

        System.out.println("Server is listening on port : " + port + "!");

        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            System.err.println("An error occured on the server!");
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
                    id++;
                    Process client = new Process(socket, id);
                    ProcessBus.getInstance().add(client);
                    System.out.println("Process with id = " + id + " connected on " + new Date().toString());
                    executor.execute(client);
                }
            } catch (IOException ex) {
                System.err.println("An error occured on the server!");
            } finally {
                try {
                    server.close();
                } catch (IOException ex) {
                    System.err.println("An error occured on the server!");
                }
            }
        }
    }
}