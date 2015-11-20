/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.fxpaint;

import javafx.scene.shape.Shape;

public interface PaintCanvas {

    /**
     * Add a {@link Shape} to the canvas.
     *
     * @param shape The shape.
     */
    void addShape(Shape shape);
}
