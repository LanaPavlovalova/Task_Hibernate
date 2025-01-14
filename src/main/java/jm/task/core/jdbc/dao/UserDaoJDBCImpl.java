package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "age SMALLINT" +
                ")";
        executeUpdate(sql);
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        executeUpdate(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        executeUpdateWithTransaction(sql, name, lastName, age);
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        executeUpdateWithTransaction(sql, id);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        executeUpdate(sql);
    }

    private void executeUpdate(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error executing update: " + e.getMessage());
        }
    }

    private void executeUpdateWithTransaction(String sql, Object... params) {
        try {
            connection.setAutoCommit(false); // Отключаем autocommit

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (int i = 0; i < params.length; i++) {
                    if (params[i] instanceof String) {
                        statement.setString(i + 1, (String) params[i]);
                    } else if (params[i] instanceof Byte) {
                        statement.setByte(i + 1, (Byte) params[i]);
                    } else if (params[i] instanceof Long) {
                        statement.setLong(i + 1, (Long) params[i]);
                    }
                }
                statement.executeUpdate();
            }

            connection.commit(); // Фиксируем транзакцию
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Откатываем транзакцию в случае ошибки
                }
            } catch (SQLException ex) {
                System.out.println("Error during rollback: " + ex.getMessage());
            }
            System.out.println("Error executing update with transaction: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // Включаем autocommit обратно
                }
            } catch (SQLException e) {
                System.out.println("Error resetting autocommit: " + e.getMessage());
            }
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}