package com.uniquindio.finalproject.unievents;


import java.io.*;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class StartUpController extends BaseController{

    private DataUniEvent dataUniEvent;
    @FXML
    private Button signUpButton;
    @FXML
    private Button signInBtn;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private StackPane container;
    @FXML
    private TextField nameField;
    @FXML
    private TextField lastNames;
    @FXML
    private TextField idUser;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField mailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void signInAction(ActionEvent event){
        handleSignUpAction("/signIn-UI.fxml");
    }
    @FXML
    public void signUpAction(ActionEvent event){
        if (!validateFields()) return;
        
        int id = Integer.parseInt(idUser.getText());
        User user = new User(id, nameField.getText(), lastNames.getText(), phoneField.getText(), mailField.getText(), passwordField.getText());
        //Revisar esta condition luego
        if(searchUser(user).isPresent()){
            showAlert(AlertType.ERROR, "Registration Error", "Try Again cause that User is already registered!");
            return;
        }
        saveUser("User_Vefication.ser", user);

        handleSignUpAction("/verification-UI.fxml");
    }

    private boolean validateFields() {
        return (validateName() && validateLastNames() && validateIdUser() && validatePhone() && validateMail() && validatePassword());
    }

    private boolean validateName() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            showAlert(AlertType.WARNING, "Validation Error", "Name field is empty.");
            return false;
        }
        return true;
    }

    private boolean validateLastNames() {
        String lastNamesText = lastNames.getText();
        if (lastNamesText.isEmpty()) {
            showAlert(AlertType.WARNING, "Validation Error", "Last names field is empty.");
            return false;
        }
        return true;
    }

    private boolean validateIdUser() {
        String idText = idUser.getText();
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Validation Error", "Invalid ID format. Please enter numbers only.");
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        String phone = phoneField.getText();
        if (phone.isEmpty() || !phone.matches("\\d+")) {
            showAlert(AlertType.WARNING, "Validation Error", "Invalid phone number. Please enter numbers only.");
            return false;
        }
        return true;
    }

    private boolean validateMail() {
        String email = mailField.getText();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email.isEmpty() || !pat.matcher(email).matches()) {
            showAlert(AlertType.WARNING, "Validation Error", "Invalid email format.");
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = passwordField.getText();
        if (password.isEmpty() || password.length() < 6) {
            showAlert(AlertType.WARNING, "Validation Error", "Password must be at least 6 characters long.");
            return false;
        }
        return true;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public Optional<User> searchUser(User user){ 
        dataUniEvent = UnieventsApplication.getDataUniEvent();
        Predicate<User> condition = u-> u.getMail().equals(user.getMail()) || u.getID() == user.getID() ;
        return dataUniEvent.getUsers().stream().filter(condition).findAny();
    }
    // @FXML
    // private void transtionScene(String path) throws IOException {
    //     Parent root = FXMLLoader.load(getClass().getResource(path));
    //     Scene scene = signUpButton.getScene();

    //     root.translateXProperty().set(scene.getWidth());
    //     container.getChildren().add(root);

    //     Timeline timeline = new Timeline();
    //     KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_BOTH);
    //     KeyFrame kf = new KeyFrame(Duration.seconds(0.8), kv);
    //     timeline.getKeyFrames().add(kf);
    //     timeline.setOnFinished(event1 -> {
    //         container.getChildren().remove(anchorRoot);
    //     });
    //     timeline.play();
    // }
    public void saveUser(String filePath, User user) {
        
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(user);
            System.out.println("Yeah USERZAO is saved dude!");
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }
    
    private void handleSignUpAction(String path) {
        try {
            transitionScene(anchorRoot, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}