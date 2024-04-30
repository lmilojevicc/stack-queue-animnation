package main;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class QueueController {
    @FXML
    private HBox queueHBox;
    @FXML
    private TextField inputField;
    private Queue<ValueSquare> queue = new LinkedList<>();

    @FXML
    private void handleEnqueueButton() {
        String value = inputField.getText();
        if (!value.isEmpty()) {
            inputField.clear();

            ValueSquare valueSquare = new ValueSquare(value);
            queueHBox.getChildren().add(valueSquare);

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), valueSquare);
            translateTransition.setFromY(-100);
            translateTransition.setToY(0);

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), valueSquare);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);

            ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);
            parallelTransition.setOnFinished(e -> {
                queue.add(valueSquare);
            });
            parallelTransition.play();
        }
    }

    @FXML
    private void handleDequeueButton() {
        if (!queue.isEmpty()) {
            ValueSquare valueSquare = queue.poll();

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), valueSquare);
            translateTransition.setToY(-100);

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), valueSquare);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);

            ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);
            parallelTransition.setOnFinished(e -> queueHBox.getChildren().remove(valueSquare));
            parallelTransition.play();
        }
    }

    @FXML
    private void handleFindButton() {
        String value = inputField.getText();
        if (!queue.isEmpty()) {
            SequentialTransition sequentialTransition = new SequentialTransition();
            boolean itemFound = false;

            while (!queue.isEmpty()) {
                ValueSquare valueSquare = queue.peek();

                if (Objects.equals(valueSquare.getVal(), value)) {
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));
                    sequentialTransition.getChildren().add(pauseTransition);

                    sequentialTransition.setOnFinished(event -> {
                        valueSquare.getRectangle().setFill(Color.valueOf("F6D966FF"));

                        PauseTransition restoreTransition = new PauseTransition(Duration.seconds(3));
                        restoreTransition.setOnFinished(restoreEvent -> {
                            valueSquare.getRectangle().setFill(Color.TRANSPARENT);
                        });

                        restoreTransition.play();
                    });

                    itemFound = true;
                    break;
                } else {
                    queue.poll();
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
                    sequentialTransition.getChildren().add(pauseTransition);

                    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), valueSquare);
                    translateTransition.setToY(-100);

                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), valueSquare);
                    fadeTransition.setFromValue(1);
                    fadeTransition.setToValue(0);

                    ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);
                    parallelTransition.setOnFinished(e -> queueHBox.getChildren().remove(valueSquare));

                    sequentialTransition.getChildren().add(parallelTransition);
                }


            }

            sequentialTransition.play();

            if (!itemFound) {
                sequentialTransition.setOnFinished(event -> {
                    Label messageLabel = new Label("Item not in queue!");
                    messageLabel.setStyle("-fx-text-fill: red;");
                    queueHBox.getChildren().add(messageLabel);

                    PauseTransition clearMessageTransition = new PauseTransition(Duration.seconds(2));
                    clearMessageTransition.setOnFinished(clearEvent -> {
                        queueHBox.getChildren().remove(messageLabel);
                    });

                    clearMessageTransition.play();
                });
            }

        }
    }

    @FXML
    private void handleMainMenuButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("home-menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
