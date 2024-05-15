package com.example.projectfinal;

import java.sql.*;
import java.util.regex.*;

public class Register {


    private String username;
    private String pass;
    private String email;
    private String first;
    private String last;
    private String Bio;

    Register(String email, String pass, String username, String first, String last, String Bio){
        this.username=username;
        this.pass=pass;
        this.email=email;
        this.first=first;
        this.last=last;
        this.Bio=Bio;
    }

    String regex = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    Pattern pattern = Pattern.compile(regex);



    public boolean registerUser() {

        Matcher matcher = pattern.matcher(email);

        if ( pass.length()<8 || Character.isUpperCase(pass.charAt(0))== false)
        {
            System.out.println("Wrong Password please try again\n");
            return false;
        }
        else if ( matcher.matches()== false ) {
            System.out.println("Wrong Email please try again\n");
            return false;
        }else
        {
            String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
            String userName = "root";
            String dbPassword = "123456";

            try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {
                String dbName = " db ";
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
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }


    }
}


