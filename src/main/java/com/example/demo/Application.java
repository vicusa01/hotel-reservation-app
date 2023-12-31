package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    private static Stage stg;
    @Override
    public void start(Stage stage) throws IOException {

        stg=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("sample.fxml"));
        stage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Falcon");
        stage.setScene(new Scene(root, 750, 400));
        stage.show();

    }
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }


    public static void main(String[] args) {
        launch();
    }
}