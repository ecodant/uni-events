package com.uniquindio.finalproject.unievents;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class VerificationController extends BaseController {
    private int verificationCode;
    @FXML
    private Button btnConfirm;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private TextField labelValidation;
    
    @Autowired
    private EmailSenderService senderService;  // Inject EmailSenderService
    
    private User user = null;
    private DataUniEvent dataUniEvent;

    public VerificationController() {
        // this.dataUniEvent = UnieventsApplication.getDataUniEvent();
    }
    
    public void initialize() {
            verificationCode = generateCode();
            user = loadFromFile("D:\\Java Projects\\uni-events\\User_Vefication.ser");
            UnieventsApplication app = UnieventsApplication.getInstance();
            app.sendMail(user.getMail(), "Uni-Events Confirmation", generateEmailBody(verificationCode));
    }

    public void confirmationMail() {

        if(!validateInputCode(verificationCode)){
            return;
        };        
        DataUniEvent dataUniEvent = UnieventsApplication.getDataUniEvent();
        dataUniEvent.addUser(user);
        //Spawm System jaja
        NotificationService notification = dataUniEvent.getService();
        notification.subscribe(new EmailMsgListener(user.getMail()));
        
        //dataUniEvent.saveToFile("dataUniEvent.ser"); 
        handleSignUpAction();
    }
    
    private void handleSignUpAction() {
        try {
                // Get the controller and pass the user I
            transitionScene(anchorRoot, "/signIn-UI.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int generateCode() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
    private boolean validateInputCode(int codeSent) {
        String userCode = labelValidation.getText();
        
        if (userCode.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Validation Error", "The field is empty!");
            return false;
        }
        try {
            int parsedValue = Integer.parseInt(userCode);
            if (parsedValue == codeSent) {
                return true;
            } else {
                showAlert(AlertType.INFORMATION, "The code isn't matched", "Please check again cause the code isn't matched");
                return false;
            }
          } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Invalid Type", "Your input isn't a number!");
            labelValidation.setText("");
          }
        return false;
    }
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // public User loadUser(String filePath) {
    //     try (FileInputStream fileIn = new FileInputStream(filePath);
    //          ObjectInputStream in = new ObjectInputStream(fileIn)) {
    //         User user = (User) in.readObject();
    //         System.out.println("User data loaded successfully.");
    //         return user;
    //     } catch (IOException | ClassNotFoundException e) {
    //         System.out.println("Error loading user data: " + e.getMessage());
    //         return null;
    //     }
    // }
    private User loadFromFile(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (User) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String generateEmailBody(int verificationCode) {
        return "Dear User,\n\n" +
               "Thank you for registering with UniEventos. To complete your registration, please use the following verification code:\n\n" +
               "Verification Code: " + verificationCode + "\n\n" +
               "Please enter this code on the verification page to activate your account.\n\n" +
               "If you did not request this code, please ignore this email.\n\n" +
               "Best regards,\n" +
               "UniEventos Team";
    }

}
