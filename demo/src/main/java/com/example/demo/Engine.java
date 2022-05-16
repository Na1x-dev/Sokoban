package com.example.demo;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Engine {
    private final Button player;
    private final Label statusLabel;
    private final AnchorPane gameField;
    private final Button nextLevelButton;
    private double newPlayerLayoutX;
    private double newPlayerLayoutY;
    boolean isMoveButtonPressed;

    Engine(AnchorPane gameField, Button player, Label statusLabel, Button nextLevelButton) {
        this.gameField = gameField;
        this.player = player;
        this.statusLabel = statusLabel;
        this.nextLevelButton = nextLevelButton;
        isMoveButtonPressed = false;
    }

    public void move(KeyEvent event){
        if (!isMoveButtonPressed) {
            switch (event.getCode()) {
                case W -> moveUp();
                case S -> moveDown();
                case A -> moveLeft();
                case D -> moveRight();
            }
            isMoveButtonPressed = true;
        }
        if (checkPlaces()) {
            statusLabel.setText("Win");
            nextLevelButton.setDisable(false);
        }
    }

    public void stop(){
        isMoveButtonPressed = false;
    }

    private void moveUp() {
        newPlayerLayoutX = player.getLayoutX();
        newPlayerLayoutY = player.getLayoutY() - 50;
        playerMove();
    }

    private void moveDown() {
        newPlayerLayoutX = player.getLayoutX();
        newPlayerLayoutY = player.getLayoutY() + 50;
        playerMove();
    }

    private void moveLeft() {
        newPlayerLayoutX = player.getLayoutX() - 50;
        newPlayerLayoutY = player.getLayoutY();
        playerMove();
    }

    private void moveRight() {
        newPlayerLayoutX = player.getLayoutX() + 50;
        newPlayerLayoutY = player.getLayoutY();
        playerMove();
    }

    private void playerMove() {
        if (playerCanMove()) {
            playerAndBoxCollision();
            if (!isPlayerMoveTwoBoxes()) {
                player.setLayoutX(newPlayerLayoutX);
                player.setLayoutY(newPlayerLayoutY);
            }
        }
    }

    private boolean playerCanMove() { //проверка, не упирается ли игрок в стену
        return gameField.getChildren().stream()
                .filter(x -> x.getId().contains("wall"))
                .noneMatch(x -> x.getLayoutX() == newPlayerLayoutX && x.getLayoutY() == newPlayerLayoutY);
    }

    private void playerAndBoxCollision() { //если игрок на своем пути встречает ящик, то пробует двигать его
        Button box = (Button) gameField.getChildren().stream()
                .filter(x -> x.getId().contains("box"))
                .filter(x -> x.getLayoutX() == newPlayerLayoutX && x.getLayoutY() == newPlayerLayoutY)
                .findFirst().orElse(null);
        if (box != null) {
            boxMove(box);
        }
    }

    private void boxMove(Button box) {
        double newBoxLayoutX = box.getLayoutX() + newPlayerLayoutX - player.getLayoutX();
        double newBoxLayoutY = box.getLayoutY() + newPlayerLayoutY - player.getLayoutY();
        if (boxCanMove(newBoxLayoutX, newBoxLayoutY)) { //может ли ящик двигаться
            box.setLayoutX(newBoxLayoutX);
            box.setLayoutY(newBoxLayoutY);
        }
    }

    private boolean boxCanMove(double newBoxLayoutX, double newBoxLayoutY) { //возвращает false, если на пути движения ящика стоит другой ящик или стена
        return gameField.getChildren()
                .stream()
                .filter(x -> x.getId().contains("wall") || x.getId().contains("box"))
                .noneMatch(x -> x.getLayoutX() == newBoxLayoutX && x.getLayoutY() == newBoxLayoutY);
    }

    private boolean isPlayerMoveTwoBoxes() { //возвращает true, если ящик, который пробовал двигать игрок, остался на месте
        return gameField.getChildren().stream()
                .filter(x -> x.getId().contains("box"))
                .anyMatch(x -> x.getLayoutX() == newPlayerLayoutX && x.getLayoutY() == newPlayerLayoutY);
    }

    private boolean checkPlaces() { //проверка мест для ящиков, все ли они заняты
        List<Node> places = gameField.getChildren().stream()
                .filter(x -> x.getId().contains("place")).toList();
        List<Node> boxes = gameField.getChildren().stream()
                .filter(x -> x.getId().contains("box")).toList();
        int count = 0;
        for (Node place : places) {
            for (Node box : boxes)
                if (place.getLayoutX() == box.getLayoutX()
                        && place.getLayoutY() == box.getLayoutY()) {
                    count++;
                }
        }
        return count == places.size();
    }
}
