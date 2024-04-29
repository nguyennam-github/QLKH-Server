/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.services;

import hvan.qlkh.dao.ProductDAO;
import hvan.qlkh.dao.UserDAO;
import hvan.qlkh.models.Product;
import hvan.qlkh.models.ProductList;
import hvan.qlkh.models.User;
import hvan.qlkh.models.UserList;
import hvan.qlkh.utils.FileUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.Date;
import javax.xml.bind.JAXB;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

public class Process implements Runnable{

    private Socket socket;
    private int id;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;
    private static final String PRODUCTS_XML_FILE = "product.xml";
    private static final String USERS_XML_FILE = "user.xml";
    private static final String METHOD_RESPONSE = "Reset/";

    public Process(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
        isClosed = false;
    }

    public BufferedReader getIs() {
        return is;
    }

    public BufferedWriter getOs() {
        return os;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        try {

            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String request;
            String response;

            while (!isClosed) {

                request = is.readLine();

                if (request == null) {
                    break;
                }
                else{

                    String method = request.substring(0, request.indexOf("/"));
                    String payload = request.substring(request.indexOf("/") + 1);

                    if(method.equals("Get")){
                        ProductList pl = (ProductList) FileUtils.readXMLFile(PRODUCTS_XML_FILE, ProductList.class);
                        StringWriter sw = new StringWriter();
                        JAXB.marshal(pl, sw);
                        String xmlString = sw.toString();
                        response =  METHOD_RESPONSE + xmlString;
                        ProcessBus.getInstance().boardCast(response);
                        System.out.println("Process with id = " + id + " get/products on " + new Date().toString());
                    }
                    if(method.equals("Create")){
                        StringBuilder sb = new StringBuilder();
                        sb.append(payload);
                        while (true) {
                            String line = is.readLine();
                            if (line.trim().equals("")){
                                break;
                            }
                            sb.append(line);
                        }
                        String productXML = sb.toString();
                        Product product = JAXB.unmarshal(new StringReader(productXML), Product.class);
                        ProductDAO.getInstance().create(product);
                        ProductList pl = (ProductList) FileUtils.readXMLFile(PRODUCTS_XML_FILE, ProductList.class);
                        StringWriter sw = new StringWriter();
                        JAXB.marshal(pl, sw);
                        String xmlString = sw.toString();
                        response =  METHOD_RESPONSE + xmlString;
                        ProcessBus.getInstance().boardCast(response);
                        System.out.println("Process with id = " + id + " create/products/${id = " + product.getId() + "} on " + new Date().toString());
                    }
                    if(method.equals("Update")){
                        String productId = payload.substring(0, payload.indexOf("/"));
                        StringBuilder sb = new StringBuilder();
                        sb.append(payload.substring(payload.indexOf("/") + 1));
                        while (true) {
                            String line = is.readLine();
                            if (line.trim().equals("")){
                                break;
                            }
                            sb.append(line);
                        }
                        String productXML = sb.toString();
                        Product product = JAXB.unmarshal(new StringReader(productXML), Product.class);
                        ProductDAO.getInstance().update(productId, product);
                        ProductList pl = (ProductList) FileUtils.readXMLFile(PRODUCTS_XML_FILE, ProductList.class);
                        StringWriter sw = new StringWriter();
                        JAXB.marshal(pl, sw);
                        String xmlString = sw.toString();
                        response =  METHOD_RESPONSE + xmlString;
                        ProcessBus.getInstance().boardCast(response);
                        System.out.println("Process with id = " + id + " update/products/${id = " + productId + " -> " + product.getId() + "} on " + new Date().toString());
                    }
                    if(method.equals("Delete")){
                        String productId = payload;
                        ProductDAO.getInstance().delete(productId);
                        ProductList pl = (ProductList) FileUtils.readXMLFile(PRODUCTS_XML_FILE, ProductList.class);
                        StringWriter sw = new StringWriter();
                        JAXB.marshal(pl, sw);
                        String xmlString = sw.toString();
                        response =  METHOD_RESPONSE + xmlString;
                        ProcessBus.getInstance().boardCast(response);
                        System.out.println("Process with id = " + id + " delete/products/${id = " + productId + "} on " + new Date().toString());
                    }

                    if(method.equals("Get-User")){
                        UserList ul = (UserList) FileUtils.readXMLFile(USERS_XML_FILE, UserList.class);
                        StringWriter sw = new StringWriter();
                        JAXB.marshal(ul, sw);
                        String xmlString = sw.toString();
                        response =  "Reset-User//" + xmlString;
                        ProcessBus.getInstance().boardCast(response);
                        System.out.println("Process with id = " + id + " get/users on " + new Date().toString());
                    }
                    if(method.equals("Create-User")){
                        StringBuilder sb = new StringBuilder();
                        sb.append(payload);
                        while (true) {
                            String line = is.readLine();
                            if (line.trim().equals("")){
                                break;
                            }
                            sb.append(line);
                        }
                        String userXML = sb.toString();
                        User user = JAXB.unmarshal(new StringReader(userXML), User.class);
                        UserDAO.getInstance().create(user);
                        UserList ul = (UserList) FileUtils.readXMLFile(USERS_XML_FILE, UserList.class);
                        StringWriter sw = new StringWriter();
                        JAXB.marshal(ul, sw);
                        String xmlString = sw.toString();
                        response =  "Reset-User//" + xmlString;
                        ProcessBus.getInstance().boardCast(response);
                        System.out.println("Process with id = " + id + " create/users/${username = " + user.getUsername() + "} on " + new Date().toString());
                    }
                    if(method.equals("Update-User")){
                        String username = payload.substring(0, payload.indexOf("/"));
                        StringBuilder sb = new StringBuilder();
                        sb.append(payload.substring(payload.indexOf("/") + 1));
                        while (true) {
                            String line = is.readLine();
                            if (line.trim().equals("")){
                                break;
                            }
                            sb.append(line);
                        }
                        String userXML = sb.toString();
                        User user = JAXB.unmarshal(new StringReader(userXML), User.class);
                        UserDAO.getInstance().update(username, user);
                        UserList ul = (UserList) FileUtils.readXMLFile(USERS_XML_FILE, UserList.class);
                        StringWriter sw = new StringWriter();
                        JAXB.marshal(ul, sw);
                        String xmlString = sw.toString();
                        response =  "Reset-User/" + username + "/" + xmlString;
                        ProcessBus.getInstance().boardCast(response);
                        System.out.println("Admin update/users/${username = " + username + "} on " + new Date().toString());
                    }
                    if(method.equals("Delete-User")){
                        String username = payload;
                        UserDAO.getInstance().delete(username);
                        UserList ul = (UserList) FileUtils.readXMLFile(USERS_XML_FILE, UserList.class);
                        StringWriter sw = new StringWriter();
                        JAXB.marshal(ul, sw);
                        String xmlString = sw.toString();
                        response =  "Reset-User/" + username + "/" + xmlString;
                        ProcessBus.getInstance().boardCast(response);
                        System.out.println("Admin delete/users/${username = " + username + "} on " + new Date().toString());
                    }
                }
            }
        } catch (IOException e) {
            isClosed = true;
            System.out.println("Process with id = " + id + " disconnected on " + new Date().toString());
            ProcessBus.getInstance().remove(id);
        }
    }
    public void write(String message) throws IOException{
        os.write(message);
        os.newLine();
        os.flush();
    }
}
