package jm.task.core.jdbc;

import java.util.List;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();


        // Создание таблицы
        userDaoHibernate.createUsersTable();

        // Сохранение и вывод созданного юзера
        userDaoHibernate.saveUser("Name1", "LastName1", (byte) 30);
        userDaoHibernate.saveUser("Name2", "LastName2", (byte) 25);
        userDaoHibernate.saveUser("Name3", "LastName3", (byte) 40);
        userDaoHibernate.saveUser("Name4", "LastName4", (byte) 35);

        // Вывод всех юзеров
        List<User> users = userDaoHibernate.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userDaoHibernate.removeUserById(1);

        // Удаление всех юзеров
        userDaoHibernate.cleanUsersTable();

        // Удаление таблицы совсем
        userDaoHibernate.dropUsersTable();

        Util.shutdown();
    }
}