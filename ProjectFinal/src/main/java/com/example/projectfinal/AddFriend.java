package com.example.projectfinal;

import java.sql.*;

public class AddFriend {

    int id;
    String name;

    public AddFriend(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
    public void Manager()
    {
        authenticateName();
    }
    public void registerFriend() {
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String userName = "root";
        String dbPassword = "123456";
        try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {
            String dbName = "db";
            String selectDbQuery = "USE " + dbName;

            try (Statement statement = connection.createStatement()) {
                statement.execute(selectDbQuery);
            }


            // Prepare SQL query
            String sql = "INSERT INTO friendslist (Follwer, Follwed) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters
            statement.setInt(1, id);
            statement.setString(2, name);
            // Execute query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Friend Added successful!");
            } else {
                System.out.println("failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void authenticateName() {
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String userName = "root";
        String dbPassword = "123456";
        try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {

            String dbName = "db";
            String selectDbQuery = "USE " + dbName;

            try (Statement statement = connection.createStatement()) {
                statement.execute(selectDbQuery);
            }



            String query = "SELECT * FROM users WHERE userName = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        registerFriend();
                    } else {
                        System.out.println("Username not found");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}