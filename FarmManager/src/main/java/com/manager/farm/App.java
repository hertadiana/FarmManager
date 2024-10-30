package com.manager.farm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("src/main/resources/Fxml/Farm.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(url);
        Scene scene = new Scene(parent);
        stage.setTitle("Farm Manager");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
