package ru.geekbrains.core;

import ru.geekbrains.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AuthController {

    private static final String DB_DRIVER_NAME = "org.sqlite.JDBC";
    private static final String DB_CONNECTION_STRING = "jdbc:sqlite:main.db";
    HashMap<String, User> users = new HashMap<>();

    public void init() {
        for (User user : receiveUsers()) {
            users.put(user.getLogin(), user);
        }
    }

    public String getNickname(String login, String password) {
        User user = users.get(login);
        if (user != null && user.isPasswordCorrect(password)) {
            return user.getNickname();
        }
        return null;
    }

    //1. Добавить в сетевой чат авторизацию через базу данных SQLite.
    private ArrayList<User> receiveUsers() {
        ArrayList<User> usersArr = new ArrayList<>();
        Connection connection = getDBConnection();
        if (connection == null) return usersArr;
        try {
            Statement usersSelection = connection.createStatement();
            usersSelection.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "    id       INTEGER PRIMARY KEY AUTOINCREMENT" +
                            "                     NOT NULL," +
                            "    login    STRING  UNIQUE" +
                            "                     NOT NULL," +
                            "    password STRING  NOT NULL," +
                            "    nick     STRING  NOT NULL" +
                            ")");
            ResultSet selection = usersSelection.executeQuery("" +
                            "SELECT login," +
                            "       password," +
                            "       nick" +
                            "  FROM users");

            if (selection.next()) {
                usersArr.add(new User(
                        selection.getString(1),
                        selection.getString(2),
                        selection.getString(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Una puta brujeria esta pasando: " + e.getMessage());
        } finally {
            closeDBConnection(connection);
        }
        return usersArr;
    }

    //Добавил пару функций, которые могут понадобиться
    private User getUserByLogin(String login) {
        Connection connection = getDBConnection();
        User user = null;
        if (connection == null) return user;
        try {
            PreparedStatement userSearch = connection.prepareStatement("" +
                    "SELECT login," +
                    "       password," +
                    "       nick" +
                    "  FROM users" +
                    " WHERE login = ?" +
                    " LIMIT 1");

            userSearch.setString(1, login);
            ResultSet selection = userSearch.executeQuery();
            if (selection.next()) {
                user = new User(
                        selection.getString(1),
                        selection.getString(2),
                        selection.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException("So, NVidia, eff you (c) Linus Torvalds: " + e.getMessage());
        } finally {
            closeDBConnection(connection);
        }
        return user;
    }

    //Добавил пару функций, которые могут понадобиться
    public boolean addUser(String login, String password, String nick) {
        Connection connection = getDBConnection();
        int usersAdded = 0;
        if (connection == null) return false;
        try {
            connection.setAutoCommit(false);
            Statement usersCreation = connection.createStatement();
            usersCreation.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "    id       INTEGER PRIMARY KEY AUTOINCREMENT" +
                            "                     NOT NULL," +
                            "    login    STRING  UNIQUE" +
                            "                     NOT NULL," +
                            "    password STRING  NOT NULL," +
                            "    nick     STRING  NOT NULL" +
                            ")");

            PreparedStatement usersAddition = connection.prepareStatement("INSERT INTO users (login, password, nick) VALUES(?, ?, ?)");

            usersAddition.setString(1, login);
            usersAddition.setString(2, password);
            usersAddition.setString(3, nick);

            usersAdded = usersAddition.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Una puta brujeria esta pasando: " + e.getMessage());
        } finally {
            closeDBConnection(connection);
        }
        return usersAdded > 0;
    }

    //2. Добавить в сетевой чат возможность смены ника.
    //Не стал реализовывать клиентскую часть, т. к. много несделанных домашек ))
    //Серверная часть реализована ниже.
    //Почему сразу не сделал UPDATE users WHERE login = ?
    //Так как на таблицу может попасть блокировка, пока будут удаляться записи
    //Видел на техностриме mail.ru
    public int updateUserNickname(String login, String nick) {
        Connection connection = getDBConnection();
        int usersUpdated = 0;
        if (connection == null) return usersUpdated;
        try {
            PreparedStatement usersUpdate = connection.prepareStatement("" +
                    "UPDATE users" +
                    "   SET nick = ?" +
                    " WHERE id IN (" +
                    "    SELECT id" +
                    "      FROM users" +
                    "     WHERE login = ?" +
                    ")");

            usersUpdate.setString(2, login);
            usersUpdate.setString(1, nick);

            usersUpdated = usersUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Что-то пошло не так: " + e.getMessage());
        } finally {
            closeDBConnection(connection);
        }
        return usersUpdated;
    }

    private Connection getDBConnection() {
        Connection funcResult = null;
        try {
            Class.forName(DB_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Your class wasn't found, filthy human! " + e.getMessage());
        }
        try {
            funcResult = DriverManager.getConnection(DB_CONNECTION_STRING);
        } catch (SQLException e) {
            throw new RuntimeException("Y ahora que? Tu base de datos no esta: " + e.getMessage());
        }
        return funcResult;
    }

    private void closeDBConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Use the power of almighty reboot: " + e.getMessage());
        }
    }

}