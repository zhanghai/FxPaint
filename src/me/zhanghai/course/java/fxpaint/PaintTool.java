/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.fxpaint;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public interface PaintTool {

    void onMousePressed(MouseEvent event, Pane canvas);

    void onMouseDragged(MouseEvent event, Pane canvas);

    void onMouseReleased(MouseEvent event, Pane canvas);

    void onEnd();
}
