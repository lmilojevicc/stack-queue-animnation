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
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Stack;
import main.ValueSquare;

public class StackController {
    @FXML
    private VBox stackVBox;

    @FXML
    private TextField inputField;

    private Stack<ValueSquare> stack = new Stack<>();

    @FXML
    private void handlePushButton(ActionEvent event) {
        String value = inputField.getText();
        if (!value.isEmpty()) {
            inputField.clear();
            ValueSquare ValueSquare = new ValueSquare(value);
            stackVBox.getChildren().add(0, ValueSquare);

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), ValueSquare);
            translateTransition.setFromY(-100);
            translateTransition.setToY(0);

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), ValueSquare);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);

            ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);
            parallelTransition.setOnFinished(e -> {
                stack.push(ValueSquare);
            });
            parallelTransition.play();
        }
    }

    @FXML
    private void handlePopButton(ActionEvent event) {
        if (!stack.isEmpty()) {
            ValueSquare ValueSquare = stack.pop();

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), ValueSquare);
            translateTransition.setToY(-100);

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), ValueSquare);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);

            ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);
            parallelTransition.setOnFinished(e -> stackVBox.getChildren().remove(ValueSquare));
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
