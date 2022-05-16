package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuController {

    @FXML
    void play(ActionEvent event) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Sokoban");
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("level1.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene second= new Scene(root);
        primaryStage.setScene(second);
        primaryStage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
