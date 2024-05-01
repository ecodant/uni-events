
package com.uniquindio.finalproject;

// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.ObjectOutputStream;
import java.io.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StartUpController {

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

    public void saveData(ActionEvent event){
        // Extract user data from text fields
        int id;
        try {
            id = Integer.parseInt(idUser.getText());
        } catch (NumberFormatException e) {
            // Handle invalid ID format (e.g., display error message)
            System.out.println("Error: Invalid ID format. Please enter numbers only.");
            return;
        }
        //Para el futuro, validar los datos de los fields.
        User user = new User(id, nameField.getText(), lastNames.getText(), phoneField.getText(), mailField.getText(), passwordField.getText());
        // System.out.println("THIS THE OBJ: "+ user);
        // Serialize user object (with try/catch for potential exceptions)
        try {
            FileOutputStream fileOut = new FileOutputStream("userData.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            fileOut.close();
            out.close();
            System.out.println("User data saved successfully.");
        }   
        catch (IOException e) {
            // Handle serialization exceptions (e.g., file access issues)
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }
}
