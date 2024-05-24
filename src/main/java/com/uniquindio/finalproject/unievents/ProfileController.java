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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ProfileController extends BaseController {
    @FXML
    private Text nameField;
    @FXML
    private Text mailField;
    @FXML
    private Text phoneField;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button getTickets;
    @FXML
    private Button signOut;
    private DataUniEvent dataUniEvent;
    private User user;
    // public void initialize() {
    //     // DataUniEvent dataUniEvent = UnieventsApplication.getDataUniEvent();
    // }
    public void backHome(ActionEvent event){
        handleSignUpAction("/startup-UI.fxml");
    }
    public void buyTickets() throws IOException{
        moveToBuy(user);
    }
    private void handleSignUpAction(String path) {
        try {
            transitionScene(anchorRoot, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void moveToBuy(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/buy-panel.fxml"));
        Parent root = loader.load();
        
        BuyPanelController buyController = loader.getController();
        buyController.setUser(user);

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
    public void setUserData(User user) {
        nameField.setText(user.getName()+ " " + user.getLastname());
        mailField.setText(user.getMail());
        phoneField.setText(user.getPhoneNumber());
    }
    public void setUser(User user){
        this.user = user;
    }
}
