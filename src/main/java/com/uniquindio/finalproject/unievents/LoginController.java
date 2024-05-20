package com.uniquindio.finalproject.unievents;

import java.io.IOException;
import java.util.Optional;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class LoginController extends BaseController {
    @FXML
    private TextField mailField;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInButton;
    private DataUniEvent dataUniEvent;

    @FXML
    public void validateUser(ActionEvent event) {
        dataUniEvent = UnieventsApplication.getDataUniEvent();
        if (!validateFields()) {
            return;
        }
        System.out.println(passwordField.getText());
        System.out.println(mailField.getText());
        if (passwordField.getText().equals("admin") && mailField.getText().equals("admin@unieventos.com")) {
            handleSignUpAction("/admin-panel.fxml");
        } else {
            Optional<User> userOptional = dataUniEvent.loginChecker(passwordField.getText(), mailField.getText());
            if (userOptional.isPresent()) {
                try {
                    moveToProfile(userOptional.get());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Login Fails", "Check your password or mail please!");
            }
        }
    }

    private void moveToProfile(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile-screen.fxml"));
        Parent root = loader.load();
        
        ProfileController profileController = loader.getController();
        profileController.setUserData(user);

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

    private void handleSignUpAction(String path) {
        try {
            transitionScene(anchorRoot, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateFields() {
        if (mailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Validation Error", "The mail field is empty!");
            return false;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Validation Error", "The password field is empty!");
            return false;
        }
        return true;
    }
}
