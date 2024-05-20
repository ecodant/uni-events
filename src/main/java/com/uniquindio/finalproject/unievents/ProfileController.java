package com.uniquindio.finalproject.unievents;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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
    // public void initialize() {
    //     // DataUniEvent dataUniEvent = UnieventsApplication.getDataUniEvent();
    // }
    public void backHome(ActionEvent event){
        handleSignUpAction("/startup-UI.fxml");
    }
    public void buyTickets(){
        System.out.println("You're going to buy tickets e.e");
    }
    private void handleSignUpAction(String path) {
        try {
            transitionScene(anchorRoot, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUserData(User user) {
        nameField.setText(user.getName());
        mailField.setText(user.getMail());
        phoneField.setText(user.getPhoneNumber());
    }
}
