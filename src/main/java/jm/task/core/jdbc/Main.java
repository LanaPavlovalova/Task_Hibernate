package jm.task.core.jdbc;

import java.util.List;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        UserDao userDao = new UserDaoJDBCImpl();
        UserService userServiceWithDI = new UserServiceImpl(userDao);

        // Создал табличку
        userService.createUsersTable();

        // Создаю 4 юзера
        User user1 = new User(1L, "Name1", "LastName1", (byte) 30);
        User user2 = new User(2L, "Name2", "LastName2", (byte) 25);
        User user3 = new User(3L, "Name3", "LastName3", (byte) 40);
        User user4 = new User(4L, "Name4", "LastName4", (byte) 35);

        // Сохраняю и вывожу созданного юзера
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.saveUser(user4);

        // Вывожу всех юзеров
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        // Удалил всех юзеров (Очистка таблицы)
        userService.cleanUsersTable();

        // Удаляю таблицу совсем ('дропаю' таблицу)
        userService.dropUsersTable();
    }
}