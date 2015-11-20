/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.fxpaint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {

    private final PaintTools.LineTool lineTool = new PaintTools.LineTool();
    private final PaintTools.RectangleTool rectangleTool = new PaintTools.RectangleTool();
    private final PaintTools.EllipseTool ellipseTool = new PaintTools.EllipseTool();
    private final PaintTools.TextTool textTool = new PaintTools.TextTool();

    private Stage stage;

    public ToolBar toolPane;
    public Pane canvasContainerPane;
    public Pane canvasPane;
    public TextField canvasTextField;

    private PaintTool selectedTool = lineTool;

    public void initialize() {
        for (Node node : toolPane.getItems()) {
            node.getStyleClass().remove("radio-button");
            node.getStyleClass().add("toggle-button");
        }
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
        textTool.setTextField(canvasTextField);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onActionSave(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
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
        selectedTool.onMousePressed(event, canvasPane);
    }

    public void onCanvasMouseDragged(MouseEvent event) {
        selectedTool.onMouseDragged(event, canvasPane);
    }

    public void onCanvasMouseReleased(MouseEvent event) {
        selectedTool.onMouseReleased(event, canvasPane);
    }
}
