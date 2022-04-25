package au.edu.sydney.soft3202.task3.model;

import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;


public class Database {
    private static final String gameDB = "game.db";

    private static final String gameDBURL = "jdbc:sqlite:" + gameDB;

    public static void createDB() {
        File gameDBFile = new File(gameDB);

        //return if files already exist
        if (gameDBFile.exists()) {
            return;
        }

        //check that we can connect to both databases
        try (Connection ignored = DriverManager.getConnection(gameDBURL)) {
            ;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void setupDB() {
        //table creation strings
        String createUsersTableSQL =
                """
                CREATE TABLE IF NOT EXISTS users (
                    user_id integer PRIMARY_KEY,
                    username text NOT NULL
                );
                """;

        String createSaveTableSQL =
                """
                CREATE TABLE IF NOT EXISTS saves (
                    save_id integer PRIMARY KEY,
                    save_name text NOT NULL,
                    user_owner text NOT NULL,
                    serialisation text NOT NULL,
                    FOREIGN KEY(user_owner) REFERENCES users(username)
                );
                """;

        //check that we can connect to both databases
        try (Connection connection = DriverManager.getConnection(gameDBURL);
                Statement statement = connection.createStatement()) {
            statement.execute(createUsersTableSQL);
            statement.execute(createSaveTableSQL);

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    //called on application start, when user is asked to login
    public static void addUser(String username) {
        String addUserSQL =
                """
                INSERT INTO users(username) VALUES
                    (?)
                """;
        try (Connection connection = DriverManager.getConnection(gameDBURL);
             PreparedStatement preparedStatement = connection.prepareStatement(addUserSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    //add a save file to the save table with the requisite details, called when save button is initialised
    // username through current state, saveName through GUI input, serialisation through model method
    public static void addSaveFile(String username, String saveName, String serialisation) {
        String addSaveSQL =
                """
                INSERT INTO saves(user_owner, save_name, serialisation) VALUES
                    (?, ?, ?)
                """;
        try (Connection connection = DriverManager.getConnection(gameDBURL);
             PreparedStatement preparedStatement = connection.prepareStatement(addSaveSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, saveName);
            preparedStatement.setString(3, serialisation);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    //returns a resultSet (might change to string array) of all saves of the given user
    public static ArrayList<String> queryUserSaves(String username) {
        ArrayList<String> ret = new ArrayList<>();
        String allSavesQuery =
                """
                SELECT save_name
                FROM users AS u
                INNER JOIN saves AS s ON u.username = s.user_owner
                WHERE s.user_owner = ?
                """;
        try (Connection connection = DriverManager.getConnection(gameDBURL);
             PreparedStatement preparedStatement = connection.prepareStatement(allSavesQuery)) {
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                ret.add(result.getString("save_name"));
            }
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return ret;
    }

    //returns the serialisation of the savefile with given name
    //called when user selects a file to load, feeds string to modified deserialise
    public static String querySerialisation(String saveName) {
        String allSavesQuery =
                """
                SELECT serialisation
                FROM users AS u
                INNER JOIN saves AS s ON u.username = s.user_owner
                WHERE s.save_name = ?
                """;
        try (Connection connection = DriverManager.getConnection(gameDBURL);
             PreparedStatement preparedStatement = connection.prepareStatement(allSavesQuery)) {
            preparedStatement.setString(1, saveName);
            return preparedStatement.executeQuery().getString("serialisation");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

}
