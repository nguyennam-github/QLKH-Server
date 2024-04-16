/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.thread;

import hvan.qlkh.dao.ProductDAO;
import hvan.qlkh.models.Product;
import hvan.qlkh.models.ProductList;
import hvan.qlkh.utils.FileUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXB;
import static hvan.qlkh.main.Server.productsSocket;

/**
 *
 * @author PC
 */
public class ProductsAPI implements Runnable{
    
    private ServerSocket server;
    private Socket socket;
    
    private String request;
    private String response;

    public ProductsAPI(ServerSocket server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }
    
    @Override
    public void run() {
        //chờ yêu cầu từ productsSocket
        try {
            socket = server.accept();

        //Tạo input stream, nối tới Socket
        ObjectInputStream ois =
            new ObjectInputStream(socket.getInputStream()); 

        //Tạo outputStream, nối tới socketadmi
        ObjectOutputStream oos =
            new ObjectOutputStream(socket.getOutputStream());

        //Đọc thông tin từ productsSocket
        request = ois.readObject().toString();
        String method = request.substring(0, request.indexOf("/"));
        String payload = request.substring(request.indexOf("/") + 1);
        if (method.equals("get")) {
        //ghi dữ liệu ra productsSocket
            ProductList pl = (ProductList) FileUtils.readXMLFile("product.xml", ProductList.class);
            StringWriter sw = new StringWriter();
            JAXB.marshal(pl, sw);
            String xmlString = sw.toString();
            response = xmlString;
            oos.writeObject(response); 
            System.out.println(method + " from " + socket.getInetAddress() + " at " + new Date());
        }
        if (method.equals("create")){
            String productXML = payload;
            Product product = JAXB.unmarshal(new StringReader(productXML), Product.class);
            ProductDAO.getInstance().create(product);
        //ghi dữ liệu ra productsSocket
            ProductList pl = (ProductList) FileUtils.readXMLFile("product.xml", ProductList.class);
            StringWriter sw = new StringWriter();
            JAXB.marshal(pl, sw);
            String xmlString = sw.toString();
            response = xmlString;
            oos.writeObject(response); 
            System.out.println(method + "product with id=" + product.getId() + " from " + socket.getInetAddress() + " at " + new Date());
        }
        if (method.equals("update")){
            String id = payload.substring(0, payload.indexOf("/"));
            String productXML = payload.substring(payload.indexOf("/") + 1);
            Product product = JAXB.unmarshal(new StringReader(productXML), Product.class);
            ProductDAO.getInstance().update(id, product);
            //ghi dữ liệu ra productsSocket
            ProductList pl = (ProductList) FileUtils.readXMLFile("product.xml", ProductList.class);
            StringWriter sw = new StringWriter();
            JAXB.marshal(pl, sw);
            String xmlString = sw.toString();
            response = xmlString;
            oos.writeObject(response); 
            System.out.println(method + "product with id=" + product.getId() + " from " + socket.getInetAddress() + " at " + new Date());
        }
        if (method.equals("delete")){
            String id = payload;
            ProductDAO.getInstance().delete(id);
            //ghi dữ liệu ra productsSocket
            ProductList pl = (ProductList) FileUtils.readXMLFile("product.xml", ProductList.class);
            StringWriter sw = new StringWriter();
            JAXB.marshal(pl, sw);
            String xmlString = sw.toString();
            response = xmlString;
            oos.writeObject(response); 
            System.out.println(method + "product with id=" + id + " from " + socket.getInetAddress() + " at " + new Date());
        }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(hvan.qlkh.main.Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
