/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.models;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)

public class UserList {

    private static UserList instance;
    @XmlElement(name = "user")
    private List<User> users;

    private UserList() {
    }

    public static synchronized UserList getInstance(){
        if (instance == null){
            instance = new UserList();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
