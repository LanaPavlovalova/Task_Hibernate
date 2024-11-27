package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    // Конструктор с внедрением зависимости через интерфейс
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    // Конструктор по умолчанию, использующий реализацию UserDaoJDBCImpl
    public UserServiceImpl() {
        this(new UserDaoJDBCImpl());
    }

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    public void saveUser(User user) {
        userDao.saveUser(user.getName(), user.getLastName(), user.getAge());
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}