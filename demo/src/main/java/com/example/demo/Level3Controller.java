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

public class Level3Controller {
    boolean isMoveButtonPressed = false;
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
        if (!isMoveButtonPressed) {
            switch (event.getCode()) {
                case W -> engine.moveUp();
                case S -> engine.moveDown();
                case A -> engine.moveLeft();
                case D -> engine.moveRight();
            }
            isMoveButtonPressed = true;
        }
        if (engine.checkPlaces()) {
            statusLabel.setText("Win");
            nextLevelButton.setDisable(false);
        }
    }

    @FXML
    void stop(KeyEvent event) {
        isMoveButtonPressed = false;
    }

    @FXML
    void restart(ActionEvent event) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Sokoban");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("level3.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene second = new Scene(root);
        primaryStage.setScene(second);
        primaryStage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void goToMenu(ActionEvent event) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Sokoban");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        engine = new Engine(gameField, player, statusLabel);
    }

}
