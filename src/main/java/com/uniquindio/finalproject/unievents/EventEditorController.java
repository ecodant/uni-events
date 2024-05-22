package com.uniquindio.finalproject.unievents;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;

public class EventEditorController {

    private DataUniEvent dataUniEvent;
    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField addressField;
    @FXML
    private TextField imgField;

    @FXML
    private ChoiceBox<EventType> eventType;

    private Event newEvent;

    @FXML
    public void initialize() {
        eventType.getItems().setAll(EventType.values());
    }

    // Getters for the fields
    public TextField getNameField() { return nameField; }
    public TextField getDescriptionField() { return descriptionField; }
    public DatePicker getDatePicker() { return datePicker; }
    public TextField getAddressField() { return addressField; }
    public TextField getImgURL() { return imgField; }
    public ChoiceBox<EventType> getEventType() { return eventType; }
}