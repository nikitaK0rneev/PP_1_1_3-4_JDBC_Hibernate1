package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private static final String CREATE_USERS_TABLE_QUERY = "create table if not exists users" +
            "(id int primary key auto_increment, " +
            "name varchar(45), " +
            "lastname varchar(45), " +
            "age int)";
    private static final String DROP_USERS_TABLE_QUERY = "drop table if exists users";
    private static final String SAVE_USER_QUERY = "insert into users (name, lastName, age) values(?, ?, ?)";
    private static final String REMOVE_USER_BY_ID_QUERY = "delete from users where id = ?";
    private static final String GET_ALL_USERS_QUERY = "select * from users";
    private static final String CLEAN_USERS_TABLE_QUERY = "delete from users";
    private final Connection connection = getConnection();

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_USERS_TABLE_QUERY);
            System.out.println("Table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_USERS_TABLE_QUERY);
            System.out.println("Table dropped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_USER_QUERY)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(REMOVE_USER_BY_ID_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("User removed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(CLEAN_USERS_TABLE_QUERY)) {
            statement.executeUpdate();
            System.out.println("Table cleaned");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
