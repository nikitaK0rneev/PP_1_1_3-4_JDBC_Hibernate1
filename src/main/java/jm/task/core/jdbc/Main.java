package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("name1", "lastname1", (byte) 11);
        userService.saveUser("name2", "lastname2", (byte) 22);
        userService.saveUser("name3", "lastname3", (byte) 33);
        userService.saveUser("name4", "lastname4", (byte) 44);
        System.out.println(userService.getAllUsers().toString());
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
