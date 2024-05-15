package com.example.projectfinal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.*;

public class Manager {

    @FXML
    private Label MessageLabel;

    @FXML
    private TextField EmailTextField;
    @FXML
    private TextField PasswordTextField;
    @FXML
    private TextField FirstNameReg;
    @FXML
    private TextField LastNameReg;
    @FXML
    private TextField UserNameReg;
    @FXML
    private TextField EmailReg;
    @FXML
    private TextField PasswordReg;
    @FXML
    private TextField BioReg;

    public void RegisterButtonOnAction(ActionEvent event){
        String email = EmailReg.getText();
        String password = PasswordReg.getText();
        String firstname = FirstNameReg.getText();
        String lastname = LastNameReg.getText();
        String username = UserNameReg.getText();
        String Bio = BioReg.getText();

        Register reg = new Register(email, password, username, firstname, lastname, Bio);
        reg.registerUser();

    }



    public void loginButtonOnAction(ActionEvent event) throws IOException {
        String email = EmailTextField.getText();
        String password = PasswordTextField.getText();

        Login login = new Login(email, password);

        if (login.authenticateUser()) {
            MessageLabel.setText("Successful!");
            HomePageManager.setData(GetidFromEmail(email));
            openHomePage();
        } else {
            MessageLabel.setText("Invalid input. Please try again.");
        }
    }
    public int GetidFromEmail(String email) {
        try (Connection connection = getConnection()) {

            String dbName = "db";
            String selectDbQuery = "USE " + dbName;

            try (Statement statement = connection.createStatement()) {
                statement.execute(selectDbQuery);
            }

            String query = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public void openHomePage() {
        try {
            Stage stage = new Stage(); // Instantiate a new Stage object
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HomePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Home Page");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String userName = "root";
        String dbPassword = "123456";
        return DriverManager.getConnection(url, userName, dbPassword);
    }
}