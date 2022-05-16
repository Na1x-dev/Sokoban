package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Level3Controller {
    Stage primaryStage;
    Parent root;
    Engine engine;

    @FXML
    private AnchorPane gameField;

    @FXML
    private Button nextLevelButton;

    @FXML
    private Button player;

    @FXML
    private Label statusLabel;

    @FXML
    void move(KeyEvent event) {
        engine.move(event);
    }

    @FXML
    void stop(KeyEvent event) {
        engine.stop();
    }

    @FXML
    void restart(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("level3.fxml")));
        Scene second = new Scene(root);
        primaryStage.setScene(second);
        primaryStage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void goToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
        Scene second = new Scene(root);
        primaryStage.setScene(second);
        primaryStage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void nextLevel(ActionEvent event) {
        statusLabel.setText("The End");
    }

    @FXML
    void initialize() {
        engine = new Engine(gameField, player, statusLabel, nextLevelButton);
        primaryStage = new Stage();
        primaryStage.setTitle("Sokoban");
    }

}
