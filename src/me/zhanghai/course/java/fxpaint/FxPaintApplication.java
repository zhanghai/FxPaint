/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.fxpaint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxPaintApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load the FXML with FXMLLoader instance so we can call FxPaintApplication.setStage().
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxpaint.fxml"));
        Parent mainRoot = loader.load();
        FxPaintController controller = loader.getController();
        controller.setStage(primaryStage);

        // Set the scene and show the stage.
        primaryStage.setTitle("FxPaint");
        primaryStage.setScene(new Scene(mainRoot));
        primaryStage.show();
    }
}
