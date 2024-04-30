package main;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ValueSquare extends StackPane {
    private Rectangle rectangle;
    private Label label;
    private String val;

    public ValueSquare(String value) {
        this.val = value;
        rectangle = new Rectangle(50, 50, Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK); // Set the border color
        label = new Label(value);
        label.setTextFill(Color.BLACK); // Set the text color
        setAlignment(Pos.CENTER);
        getChildren().addAll(rectangle, label);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
