package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();

        // Создание таблицы
        userDao.createUsersTable();

        // Сохранение и вывод созданного юзера
        userDao.saveUser("Name1", "LastName1", (byte) 30);
        userDao.saveUser("Name2", "LastName2", (byte) 25);
        userDao.saveUser("Name3", "LastName3", (byte) 40);
        userDao.saveUser("Name4", "LastName4", (byte) 35);

        // Вывод всех юзеров
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userDao.removeUserById(1);

        // Удаление всех юзеров
        userDao.cleanUsersTable();

        // Удаление таблицы совсем
        userDao.dropUsersTable();

        Util.shutdown();
    }
}