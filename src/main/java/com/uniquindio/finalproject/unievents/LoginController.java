package com.uniquindio.finalproject.unievents;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController extends BaseController {
    @FXML
    private TextField mailField;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInButton;

    public void validateUser(ActionEvent event){

    }
    private void handleSignUpAction() {
        try {
            transitionScene(anchorRoot, "/signIn-UI.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
