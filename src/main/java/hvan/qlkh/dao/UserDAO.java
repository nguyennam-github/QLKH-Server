/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.dao;

import hvan.qlkh.models.User;
import hvan.qlkh.models.UserList;
import hvan.qlkh.utils.FileUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

public final class UserDAO {

    private static final String XMLFILE_PATH = "user.xml";
    private List<User> users;
    private static UserDAO instance;

    private UserDAO() {
        this.readUsers();
    }

    public static synchronized UserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }
        return instance;
    }

    public synchronized void writeUsers() {
        UserList ul = UserList.getInstance();
        ul.setUsers(users);
        FileUtils.writeXMLtoFile(XMLFILE_PATH, ul);
    }

    public List<User> readUsers() {
        if (users == null){
            users = new ArrayList<>();
            UserList ul = (UserList) FileUtils.readXMLFile(XMLFILE_PATH, UserList.class);
            if (ul != null &&  (ul.getUsers() != null)){
                users = ul.getUsers();
            }
        }
        return users;
    }

    public User findByUsername(String username){
        return readUsers().stream()
            .filter(temp -> username.equals(temp.getUsername()))
            .findAny()
            .orElse(null);
    }

    public void create(User user) {
        users.add(user);
        writeUsers();
    }

    public boolean delete(String username) {
        User user = findByUsername(username);
        if (user != null) {
            users.remove(user);
            writeUsers();
            return true;
        }
        return false;
    }
    public User update(String username, User user){
        User temp = null;
        for (User u: users){
            if (u.getUsername().equals(username)){
                u.setRead(user.isRead());
                u.setWrite(user.isWrite());
                temp = u;
                writeUsers();
                break;
            }
        }
        return temp;
    }
}
