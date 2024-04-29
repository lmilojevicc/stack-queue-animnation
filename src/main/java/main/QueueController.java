package main;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.ValueSquare;

import java.io.IOException;
import java.util.LinkedList;
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
    private void handleMainMenuButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("home-menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
