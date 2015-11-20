/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.fxpaint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FxPaintController implements PaintCanvas {

    private final PaintTools.LineTool lineTool = new PaintTools.LineTool();
    private final PaintTools.RectangleTool rectangleTool = new PaintTools.RectangleTool();
    private final PaintTools.EllipseTool ellipseTool = new PaintTools.EllipseTool();
    private final PaintTools.TextTool textTool = new PaintTools.TextTool();

    public Pane canvasContainerPane;
    public Pane canvasPane;
    public TextField canvasTextField;

    private Stage stage;

    private PaintTool selectedTool = lineTool;

    public void initialize() {

        final Rectangle clipRectangle = new Rectangle();
        canvasContainerPane.setClip(clipRectangle);
        canvasContainerPane.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue,
                                Bounds newValue) {
                clipRectangle.setWidth(newValue.getWidth());
                clipRectangle.setHeight(newValue.getHeight());
                canvasPane.setPrefWidth(newValue.getWidth());
                canvasPane.setPrefHeight(newValue.getHeight());
            }
        });

        canvasPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // For unfocusing children.
                if (!canvasTextField.isVisible()) {
                    canvasPane.requestFocus();
                }
                event.consume();
            }
        });

        textTool.setTextField(canvasTextField);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onActionSave(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.setInitialFileName(".svg");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                "Scalable Vector Graphics (*.svg)", "*.svg"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            if (!file.getName().contains(".")) {
                file = new File(file.getPath() + ".svg");
            }
            try {
                SvgWriter.write(canvasPane, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onActionExit(ActionEvent event) {
        stage.close();
    }

    public void onLineToolSelected(ActionEvent event) {
        setSelectedTool(lineTool);
    }

    public void onRectangleToolSelected(ActionEvent event) {
        setSelectedTool(rectangleTool);
    }

    public void onEllipseToolSelected(ActionEvent event) {
        setSelectedTool(ellipseTool);
    }

    public void onTextToolSelected(ActionEvent event) {
        setSelectedTool(textTool);
    }

    private void setSelectedTool(PaintTool tool) {
        selectedTool.onEnd();
        selectedTool = tool;
    }

    public void onCanvasMousePressed(MouseEvent event) {
        selectedTool.onMousePressed(event, this);
    }

    public void onCanvasMouseDragged(MouseEvent event) {
        selectedTool.onMouseDragged(event, this);
    }

    public void onCanvasMouseReleased(MouseEvent event) {
        selectedTool.onMouseReleased(event, this);
    }

    @Override
    public void addShape(final Shape shape) {
        shape.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedTool.onEnd();
                event.consume();
            }
        });
        shape.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedTool.onEnd();
                event.consume();
            }
        });
        shape.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedTool.onEnd();
                event.consume();
            }
        });
        shape.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shape.requestFocus();
                event.consume();
            }
        });
        shape.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                Boolean newValue) {
                if (shape.getFill() != null) {
                    shape.setFill(newValue ? Color.BLUE : Color.BLACK);
                }
                if (shape.getStroke() != null) {
                    shape.setStroke(newValue ? Color.BLUE : Color.BLACK);
                }
            }
        });
        shape.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCharacter().equals("\u007F")) {
                    canvasPane.getChildren().remove(shape);
                }
                event.consume();
            }
        });
        canvasPane.getChildren().add(shape);
    }
}
