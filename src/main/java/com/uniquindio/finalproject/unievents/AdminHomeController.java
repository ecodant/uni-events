package com.uniquindio.finalproject.unievents;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AdminHomeController extends BaseController{
    @FXML
    private Button eventBtn;
    @FXML
    private Button statsBtn;
    @FXML
    private Button cuponBtn;
    @FXML
    private AnchorPane anchorRoot;
    public void moveToEvent(ActionEvent e){
        handleSignUpAction("/admin-event.fxml");
    }
    public void backHome(ActionEvent e){
        handleSignUpAction("/startup-UI.fxml");
    }
    public void moveToStats(ActionEvent e){
        handleSignUpAction("/admin-event.fxml");
    }
    public void movetoCupon(ActionEvent e){
        handleSignUpAction("/admin-cupon.fxml");
    }
    private void handleSignUpAction(String path) {
        try {
            transitionScene(anchorRoot, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
