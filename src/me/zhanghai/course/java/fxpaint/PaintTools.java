/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.fxpaint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PaintTools {

    public static class LineTool implements PaintTool {

        private Line line;

        @Override
        public void onMousePressed(MouseEvent event, Pane canvas) {
            double x = event.getX();
            double y = event.getY();
            line = new Line(x, y, x, y);
            canvas.getChildren().add(line);
        }

        @Override
        public void onMouseDragged(MouseEvent event, Pane canvas) {

            if (line == null) {
                return;
            }

            line.setEndX(event.getX());
            line.setEndY(event.getY());
        }

        @Override
        public void onMouseReleased(MouseEvent event, Pane canvas) {
            onMouseDragged(event, canvas);
            onEnd();
        }

        @Override
        public void onEnd() {
            line = null;
        }
    }

    public static class RectangleTool implements PaintTool {

        private double x;
        private double y;
        private Rectangle rectangle;

        @Override
        public void onMousePressed(MouseEvent event, Pane canvas) {
            x = event.getX();
            y = event.getY();
            rectangle = new Rectangle(x, y, 0, 0);
            canvas.getChildren().add(rectangle);
        }

        @Override
        public void onMouseDragged(MouseEvent event, Pane canvas) {

            if (rectangle == null) {
                return;
            }

            double eventX = event.getX();
            rectangle.setX(Math.min(x, eventX));
            rectangle.setWidth(Math.abs(eventX - x));
            double eventY = event.getY();
            rectangle.setY(Math.min(y, eventY));
            rectangle.setHeight(Math.abs(eventY - y));
        }

        @Override
        public void onMouseReleased(MouseEvent event, Pane canvas) {
            onMouseDragged(event, canvas);
            onEnd();
        }

        @Override
        public void onEnd() {
            rectangle = null;
        }
    }

    public static class EllipseTool implements PaintTool {

        private double x;
        private double y;
        private Ellipse ellipse;

        @Override
        public void onMousePressed(MouseEvent event, Pane canvas) {
            x = event.getX();
            y = event.getY();
            ellipse = new Ellipse(x, y, 0, 0);
            canvas.getChildren().add(ellipse);
        }

        @Override
        public void onMouseDragged(MouseEvent event, Pane canvas) {

            if (ellipse == null) {
                return;
            }

            double eventX = event.getX();
            ellipse.setCenterX(MathUtils.mean(x, eventX));
            ellipse.setRadiusX(Math.abs(eventX - x) / 2);
            double eventY = event.getY();
            ellipse.setCenterY(MathUtils.mean(y, eventY));
            ellipse.setRadiusY(Math.abs(eventY - y) / 2);
        }

        @Override
        public void onMouseReleased(MouseEvent event, Pane canvas) {
            onMouseDragged(event, canvas);
            onEnd();
        }

        @Override
        public void onEnd() {
            ellipse = null;
        }
    }

    public static class TextTool implements PaintTool {

        private TextField textField;

        public void setTextField(TextField textField) {
            this.textField = textField;
        }

        @Override
        public void onMousePressed(MouseEvent event, Pane canvas) {}

        @Override
        public void onMouseDragged(MouseEvent event, Pane canvas) {}

        @Override
        public void onMouseReleased(MouseEvent event, final Pane canvas) {

            onEnd();

            textField.relocate(event.getX(), event.getY());
            textField.setVisible(true);
            textField.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Text text = new Text(textField.getLayoutX(),
                            textField.getLayoutY() + textField.getHeight(),
                            textField.getCharacters().toString());
                    text.setFont(textField.getFont());
                    canvas.getChildren().add(text);
                    textField.clear();
                    textField.setVisible(false);
                }
            });

            textField.requestFocus();
        }

        @Override
        public void onEnd() {
            if (textField.isVisible()) {
                textField.getOnAction().handle(null);
            }
        }
    }
}
