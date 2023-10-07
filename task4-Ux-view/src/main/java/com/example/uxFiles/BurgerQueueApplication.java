package com.example.uxFiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//
public class BurgerQueueApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurgerQueueApplication.class.getResource("main-view.fxml"));

        // Load the FXML file and create a Scene with the loaded content
        Scene scene = new Scene(fxmlLoader.load(), 820, 650);

        // Set the title for the application
        stage.setTitle("Burger Queue Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}