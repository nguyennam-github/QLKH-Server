/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class ControlBus {
    
    private static volatile ControlBus instance;
    
    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    public static ControlBus getInstance() {
        if (instance == null){
            instance = new ControlBus();
        }
        return instance;
    }

    public ControlBus() {
        clients = new ArrayList<>();
    }

    public void add(Client Client){
        clients.add(Client);
    }

    public void boardCast(String message){
        for(Client Client : instance.getClients()){
            try {
                Client.write(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public int getLength(){
        return clients.size();
    }
    public void remove(int id){
        for(int i=0; i<instance.getLength(); i++){
            if(instance.getClients().get(i).getId() == id){
                instance.clients.remove(i);
            }
        }
    }
}
