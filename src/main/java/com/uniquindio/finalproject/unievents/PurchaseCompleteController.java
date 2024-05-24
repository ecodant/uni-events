package com.uniquindio.finalproject.unievents;

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class PurchaseCompleteController extends BaseController{
    @FXML
    private AnchorPane anchorRoot;
    private User user;

    @FXML
    public void initialize() {
      
    }

    public void backProfile(ActionEvent e) throws IOException{
        moveToProfile(user);
    }
   
    private void moveToProfile(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile-screen.fxml"));
        Parent root = loader.load();
        
        ProfileController profileController = loader.getController();
        profileController.setUserData(user);
        System.out.println("User passed to the next FXML");
        profileController.setUser(user);

        Scene scene = anchorRoot.getScene();
        StackPane container = (StackPane) scene.getRoot();

        root.translateXProperty().set(scene.getWidth());
        container.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.8), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            container.getChildren().remove(anchorRoot);
        });
        timeline.play();
    }
    public void setUser(User user){
        this.user = user;
    }
    private void handleSignUpAction() {
        try {

            transitionScene(anchorRoot, "/profile-screen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
