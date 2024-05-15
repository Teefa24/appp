package com.example.projectfinal;

import java.sql.*;
import java.util.regex.*;

public class Register {


    String username;
    String pass;
    String email;
    String first;
    String last;
    String Bio;
    Register(String email, String pass, String username, String first, String last, String Bio){
        this.username=username;
        this.pass=pass;
        this.email=email;
        this.first=first;
        this.last=last;
        this.Bio=Bio;
        validation();
    }

    String regex = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    Pattern pattern = Pattern.compile(regex);


    public int validation()
    {
        Matcher matcher = pattern.matcher(email);

        if ( pass.length()<8 || Character.isUpperCase(pass.charAt(0))== false)
        {
            System.out.println("Wrong Password please try again\n");
            return 0;
        }
        else if ( matcher.matches()== false ) {
            System.out.println("Wrong Email please try again\n");
            return 0;
        }
        registerUser();
        return 0;
    }
    public boolean registerUser() {
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String userName = "root";
        String dbPassword = "123456";

        try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {


            String dbName = "db";
            String selectDbQuery = "USE " + dbName;

            try (Statement statement = connection.createStatement()) {
                statement.execute(selectDbQuery);
            }



            String sql = "INSERT INTO users (email, firstName, lastName , password,userName,Bio) VALUES (?, ?, ?, ?,?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters
            statement.setString(1, email);
            statement.setString(4, pass);
            statement.setString(2, first);
            statement.setString(3, last);
            statement.setString(5, username);
            statement.setString(6, Bio);
            // Execute query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Registration successful!");
                return true;
            } else {
                System.out.println("Registration failed!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


