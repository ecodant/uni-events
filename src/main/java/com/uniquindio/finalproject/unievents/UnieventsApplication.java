package com.uniquindio.finalproject.unievents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class UnieventsApplication extends Application{
	@Autowired
	private EmailSenderServicie senderServicie;
	public static void main(String[] args) {
		// SpringApplication.run(UnieventsApplication.class, args);
		System.out.println("HELP MEE PLEEASS");
		launch();
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		var context = SpringApplication.run(UnieventsApplication.class);
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/startup-UI.fxml"));
		var scene = new Scene(fxml.load(), 900, 600);
		stage.setScene(scene);
		stage.show();
	}
	// @EventListener(ApplicationReadyEvent.class)
	// public void sendMail(){
	// 	senderServicie.sendEmail("edwin.vinar@uqvirtual.edu.co", "Test02", "This my test mail");
	// }
}
