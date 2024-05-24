package com.uniquindio.finalproject.unievents;

import org.springframework.boot.SpringApplication;

import java.io.File;
import java.io.IOException;

import com.google.zxing.WriterException;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class UnieventsApplication extends Application {
    private DataUniEvent eventData = new DataUniEvent();

    @Autowired
    private EmailSenderService senderService;
    
    private static ConfigurableApplicationContext context;
    private static UnieventsApplication instance;
    private static DataUniEvent dataUniEvent;

    public UnieventsApplication() {
        instance = this;
    }

    public static UnieventsApplication getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        context = SpringApplication.run(UnieventsApplication.class, args);
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        File file = new File("dataUniEvent.ser");
        if (file.exists()) {
            dataUniEvent = DataUniEvent.loadFromFile("D:\\Java Projects\\uni-events\\dataUniEvent.ser");
            if (dataUniEvent == null) {
                System.out.println("Failed to load data. Initializing new DataUniEvent.");
                dataUniEvent = new DataUniEvent();
            } else {
                System.out.println("Data loaded successfully.");
            }
        } else {
            System.out.println("You're here");
            dataUniEvent = new DataUniEvent();
        }

        context.getAutowireCapableBeanFactory().autowireBean(this);
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/startup-UI.fxml"));
        var scene = new Scene(fxml.load(), 900, 600);
        // sendMailWithQRCode("edwin200431@gmail.com", "Your QR Code", "Here is your QR code:", "Some text to encode in QR code");
        stage.setTitle("Uni-Events");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Application is closing...");
        closeAppActions();
        super.stop();
    }

    public void closeAppActions() {
        dataUniEvent.saveToFile("dataUniEvent.ser");
        System.out.println("Saving data before exiting...");
    }

    public void sendMail(String mail, String subject, String body) {
        senderService.sendEmail(mail, subject, body);
    }

    public void sendMailWithQRCode(String mail, String subject, String body, String qrText) throws IOException, WriterException, MessagingException {
        senderService.sendEmailWithQRCode(mail, subject, body, qrText);
    }

    public static DataUniEvent getDataUniEvent() {
        return dataUniEvent;
    }
}
