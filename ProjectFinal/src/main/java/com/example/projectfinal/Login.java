package com.example.projectfinal;

import java.sql.*;

public class Login {

    String email;
    String pass;

    Login(String email, String pass){
        this.email=email;
        this.pass=pass;
    }

    public boolean authenticateUser() {
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String userName = "root";
        String dbPassword = "123456";

        try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {

            String dbName = "db";
            String selectDbQuery = "USE " + dbName;

            try (Statement statement = connection.createStatement()) {
                statement.execute(selectDbQuery);
            }

            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, pass);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Authentication failed
    }


}

