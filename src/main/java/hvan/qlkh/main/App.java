/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hvan.qlkh.main;

import hvan.qlkh.services.Client;
import hvan.qlkh.services.ControlBus;
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
public class App {

    public static Socket socket;
    public static ServerSocket serverSocket;
    
    public static void main(String[] args) throws IOException, JAXBException, ClassNotFoundException {
        
        ServerSocket server = null;
        int id = 0;
        System.out.println("Server is waiting to accept user...");
        
        try {
            server = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                100,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8)
        );
        
        try {
            while (true) {
                socket = server.accept();
                Client client = new Client(socket, id++);
                ControlBus.getInstance().add(client);
                System.out.println("Process with id=" + id + "is running");
                executor.execute(client);
                
            }
        } catch (IOException ex) {
        } finally {
            try {
                server.close();
            } catch (IOException ex) {
            }
        }
        
    }
}