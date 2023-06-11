package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogIn {

    public LogIn() {

    }

    @FXML
    private Button button;
    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;



    public void LogInAsAdmin(ActionEvent event) throws IOException {
        checkLogin();


    }

    public void LogInAsUser(ActionEvent event) throws IOException {
        if(username.getText().equals("javac") && password.getText().equals("123")) {
            wrongLogIn.setText("Success!");
            Application m = new Application();
            m.changeScene("main.fxml");
        }

        else if(username.getText().isEmpty() && password.getText().isEmpty()) {
            wrongLogIn.setText("Please enter your data.");
        }


        else {
            wrongLogIn.setText("Wrong username or password!");
        }
    }




    private void checkLogin() throws IOException {

        if(username.getText().equals("javacoding") && password.getText().equals("123")) {
            wrongLogIn.setText("Success!");
            Application m = new Application();
            m.changeScene("main3.fxml");


        }

        else if(username.getText().isEmpty() && password.getText().isEmpty()) {
            wrongLogIn.setText("Please enter your data.");
        }


        else {
            wrongLogIn.setText("Wrong username or password!");
        }
    }


}