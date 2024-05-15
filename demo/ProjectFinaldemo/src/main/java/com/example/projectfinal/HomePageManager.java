package com.example.projectfinal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import javax.naming.InitialContext;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HomePageManager implements Initializable
{

    static int userid = 5;
    String CurrentPost;


    @FXML
    private Label UserNameLabel;
    @FXML
    private Label BioLabel;
    @FXML
    private Label LikesCountLabel;

    @FXML
    private TextField PostTextInput;
    @FXML
    private TextField FriendNameTextInput;

    @FXML
    private TextField CommentTextField;

    @FXML
    private ListView<String> ListViewPosts;

    @FXML
    private ListView<String> ListViewFriendsList;

    @FXML
    private ListView<String> ListViewComments;


    public static void setData(int data) {
        userid = data;
        System.out.println(userid);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ReloadData();
        GetUserData();
    }

    public  void GetUserData()
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

            String query = "SELECT * FROM users WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userid);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UserNameLabel.setText(resultSet.getString("userName"));
                        BioLabel.setText(resultSet.getString("bio"));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void AddFriendBtnOnAction(ActionEvent event) {
        String FriendName = FriendNameTextInput.getText();
        AddFriend friend = new AddFriend(userid, FriendName);
        friend.Manager();
        ReloadData();
    }

    public void AddpostButtonOnAction(ActionEvent event){
        String PostContent = PostTextInput.getText();
        Post post = new Post();
        post.AddPost(PostContent);
        ReloadData();
    }

    public void ReloadData() {
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String userName = "root";
        String dbPassword = "123456";
        ObservableList<String> PostsData = null;
        try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {

            String dbName = "db";
            String selectDbQuery = "USE " + dbName;

            try (Statement statement = connection.createStatement()) {
                statement.execute(selectDbQuery);
            }
            PostsData = FXCollections.observableArrayList();

            String query = "SELECT * FROM posts";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        PostsData.add(resultSet.getString("Content"));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ListViewPosts.getItems().clear();
        ListViewPosts.getItems().addAll(PostsData);
        ListViewPosts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String GetData = String.valueOf(ListViewPosts.getSelectionModel().getSelectedItems());
                CurrentPost = GetData.replace("[", "").replace("]", "");

                UpdateComments();
                UpdateLikesCounter();

            }


        });



        ObservableList<String> Friends = null;
        try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {

            String dbName = "db";
            String selectDbQuery = "USE " + dbName;

            try (Statement statement = connection.createStatement()) {
                statement.execute(selectDbQuery);
            }
            Friends = FXCollections.observableArrayList();

            String query = "SELECT * FROM friendslist WHERE Follwer = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userid);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Friends.add(resultSet.getString("Follwed"));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        ListViewFriendsList.getItems().clear();
        ListViewFriendsList.getItems().addAll(Friends);

    }

    public void UpdateComments(){
        System.out.println(CurrentPost);

        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String userName = "root";
        String dbPassword = "123456";
        ObservableList<String> Comments = null;
        try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {

            String dbName = "db";
            String selectDbQuery = "USE " + dbName;

            try (Statement statement = connection.createStatement()) {
                statement.execute(selectDbQuery);
            }
            Comments = FXCollections.observableArrayList();

            String query = "SELECT * FROM comments WHERE Post = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, CurrentPost);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Comments.add(resultSet.getString("Content"));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        ListViewComments.getItems().clear();
        ListViewComments.getItems().addAll(Comments);
    }

    public void UpdateLikesCounter(){
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String userName = "root";
        String dbPassword = "123456";
        try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {

            String dbName = "db";
            String selectDbQuery = "USE " + dbName;

            try (Statement statement = connection.createStatement()) {
                statement.execute(selectDbQuery);
            }

            String query = "SELECT COUNT(*) AS count FROM likes WHERE Post = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, CurrentPost);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        LikesCountLabel.setText(String.valueOf(count));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void AddCommentButtonOnAction(ActionEvent event){
        String CommentContent = CommentTextField.getText();
        comments comment = new comments();
        comment.AddComment(CommentContent, CurrentPost);
        UpdateComments();
    }
    public void AddLikeButtonOnAction(ActionEvent event){
        likes like = new likes();
        like.AddLike(CurrentPost);
        UpdateLikesCounter();
    }

}
