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
 * @author Nguyễn Phan Hoài Nam
 */

public class ProcessBus {

    private static ProcessBus instance;

    private List<Process> processes;

    public List<Process> getProcesses() {
        return processes;
    }

    public static synchronized ProcessBus getInstance() {
        if (instance == null){
            instance = new ProcessBus();
        }
        return instance;
    }

    public ProcessBus() {
        processes = new ArrayList<>();
    }

    public void add(Process process){
        processes.add(process);
    }

    public void boardCast(String message){
        for(Process process : instance.getProcesses()){
            try {
                process.write(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public int getLength(){
        return processes.size();
    }
    public void remove(int id) {
        for(int i=0; i<instance.getLength(); i++) {
            if(instance.getProcesses().get(i).getId() == id && (!instance.getProcesses().isEmpty())) {
                instance.processes.remove(i);
            }
        }
    }
}
