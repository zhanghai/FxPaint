/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.fxpaint;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Writer for writing a canvas in SVG format.
 */
public class SvgWriter {

    private SvgWriter() {}

    /**
     * Write a canvas to file in SVG format.
     *
     * @param canvas The canvas.
     * @param file The file.
     * @throws IOException If writing failed.
     */
    public static void write(Region canvas, File file) throws IOException {
        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            write(canvas, writer);
        }
    }

    /**
     * Write a canvas to writer in SVG format.
     *
     * @param canvas The canvas.
     * @param writer The writer.
     * @throws IOException If writing failed.
     */
    public static void write(Region canvas, Writer writer) throws IOException {

        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
        Region viewport = (Region) canvas.getParent();
        writer.write(String.format("<svg xmlns=\"http://www.w3.org/2000/svg\"" +
                " xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"%f\"" +
                " height=\"%f\">\n", viewport.getWidth(), viewport.getHeight()));

        for (Node node : canvas.getChildrenUnmodifiable()) {
            if (node instanceof Line) {
                write((Line) node, writer);
            } else if (node instanceof Rectangle) {
                write((Rectangle) node, writer);
            } else if (node instanceof Ellipse) {
                write((Ellipse) node, writer);
            } else if (node instanceof Text) {
                write((Text) node, writer);
            }
        }

        writer.write("</svg>\n");

        writer.flush();
    }

    private static void write(Line line, Writer writer) throws IOException {
        writer.write(String.format("<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" />\n",
                line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));
    }

    private static void write(Rectangle rectangle, Writer writer) throws IOException {
        writer.write(String.format("<rect x=\"%f\" y=\"%f\" width=\"%f\" height=\"%f\" />\n",
                rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight()));
    }

    private static void write(Ellipse ellipse, Writer writer) throws IOException {
        writer.write(String.format("<ellipse cx=\"%f\" cy=\"%f\" rx=\"%f\" ry=\"%f\" />\n",
                ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusX(),
                ellipse.getRadiusY()));
    }

    private static void write(Text text, Writer writer) throws IOException {
        writer.write(String.format("<text x=\"%f\" y=\"%f\" font-size=\"%f\">%s</text>\n",
                text.getX(), text.getY(), text.getFont().getSize(), escape(text.getText())));
    }

    /**
     * Escape string for XML.
     *
     * @param string The string to escape.
     * @return The escaped string.
     */
    private static String escape(String string) {
        StringBuilder builder = new StringBuilder();
        int len = string.length();
        for (int i = 0; i < len; i++) {
            char c = string.charAt(i);
            if (c > 0x7F) {
                builder
                        .append("&#")
                        .append(Integer.toString(c, 10))
                        .append(';');
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
