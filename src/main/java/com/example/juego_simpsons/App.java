package com.example.juego_simpsons;

import com.example.juego_simpsons.vist.Vista;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(new Vista(), 1200, 680);
        stage.setTitle("SIMPSONS GAME!");
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}