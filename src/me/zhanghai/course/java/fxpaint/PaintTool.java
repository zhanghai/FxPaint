/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.fxpaint;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Interface for a paint tool.
 */
public interface PaintTool {

    /**
     * Callback upon mouse press.
     *
     * @param event Mouse event.
     * @param canvas Canvas for adding new shape.
     */
    void onMousePressed(MouseEvent event, PaintCanvas canvas);

    /**
     * Callback upon mouse drag.
     *
     * @param event Mouse event.
     * @param canvas Canvas for adding new shape.
     */
    void onMouseDragged(MouseEvent event, PaintCanvas canvas);

    /**
     * Callback upon mouse release
     *
     * @param event Mouse event.
     * @param canvas Canvas for adding new shape.
     */
    void onMouseReleased(MouseEvent event, PaintCanvas canvas);

    /**
     * Callback upon ending the tool.
     */
    void onEnd();
}
