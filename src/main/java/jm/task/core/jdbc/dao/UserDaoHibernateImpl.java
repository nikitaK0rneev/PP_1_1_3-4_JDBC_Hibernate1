package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_USERS_TABLE_QUERY = "create table if not exists users" +
            "(id int primary key auto_increment, " +
            "name varchar(45), " +
            "lastname varchar(45), " +
            "age int)";
    private static final String DROP_USERS_TABLE_QUERY = "drop table if exists users";
    private static final String SAVE_USER_QUERY = "insert into users (name, lastName, age) values(:name, :lastName, :age)";
    private static final String REMOVE_USER_BY_ID_QUERY = "delete from users where id = :id";
    private static final String GET_ALL_USERS_QUERY = "select * from users";
    private static final String CLEAN_USERS_TABLE_QUERY = "delete from users";

    //private SessionFactory fa
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_USERS_TABLE_QUERY).executeUpdate();
            transaction.commit();
            System.out.println("table created");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(DROP_USERS_TABLE_QUERY).executeUpdate();
            transaction.commit();
            System.out.println("table dropped");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(SAVE_USER_QUERY)
                    .setParameter("name", name)
                    .setParameter("lastName", lastName)
                    .setParameter("age", age)
                    .executeUpdate();
            transaction.commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                System.out.println("user add failed");
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(REMOVE_USER_BY_ID_QUERY)
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
            System.out.println("user removed");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                System.out.println("user add failed");
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()) {
            users = session.createSQLQuery(GET_ALL_USERS_QUERY)
                    .addEntity(User.class)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(CLEAN_USERS_TABLE_QUERY).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }
}
