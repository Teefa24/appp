package com.example.projectfinal;

import java.sql.*;

public class Post  {

    private  String postContents;
    private  User user;

    public Post () {

    }
    public Post (String postContents, User user) {
        this.postContents = postContents;
        this.user = user;

    }

    public void AddPost(String postContents, int userid)
    {
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String userName = "root";
        String dbPassword = "123456";

        try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {
            String dbName = "db";
            String selectDbQuery = "USE " + dbName;

            try (Statement statement = connection.createStatement()) {
                statement.execute(selectDbQuery);
            }

            String sql = "INSERT INTO posts (Content, userid) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters
            statement.setString(1, postContents);
            statement.setInt(2, userid);

            int rowsInserted = statement.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printPost() {
        System.out.println(postContents);
    }

    public void likePost(User user) {

        likes.likePost(user.getUsername());

    }

    public void unlikePost(User user) {

        likes.unlikePost(user.getUsername());

    }

    public void comment(User user, String commentContents) {
        comments.comment(user.getUsername(),commentContents);

    }
    public void deleteComment(User user, String commentContents) {
        comments.deleteComment(user.getUsername(), commentContents);

    }
    public void printDetails() {
        likes.printLikes();
        comments.PrintComments();
    }
    public void print() {
        System.out.println(this.user.getUsername() + ":\n	"+ this.postContents);
    }

    public String getPostContents() {
        return postContents;
    }
    public User getUser() {
        return user;
    }

}
