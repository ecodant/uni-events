package com.uniquindio.finalproject.unievents;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public abstract class BaseController {

    protected void transitionScene(Parent currentRoot, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = currentRoot.getScene();

        StackPane container = (StackPane) scene.getRoot();
        
        root.translateXProperty().set(scene.getWidth());
        container.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.8), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            container.getChildren().remove(currentRoot);
        });
        timeline.play();
    }
}
