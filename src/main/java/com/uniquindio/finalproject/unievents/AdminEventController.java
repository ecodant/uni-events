package com.uniquindio.finalproject.unievents;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
// import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import com.gluonhq.charm.glisten.control.Icon;

// import ch.qos.logback.core.util.Duration;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

// import javafx.animation.TranslateTransition;


public class AdminEventController extends BaseController {
    private DataUniEvent dataUniEvent;
    //Btn Event Editor
    @FXML
    private Button nextEventBtn;
    @FXML
    private Button cancelEventBtn;
    @FXML
    private Button cancelEventBtn2;
    @FXML
    private Button eventFinishBtn;
    @FXML
    private Button loadBtn;
    @FXML
    private Button EventFinishBtn;

    //Event Fields
    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField cityField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField addressField;
    @FXML
    private ChoiceBox<EventType> eventType;

    //Seat Fields

    @FXML
    private TextField capacityField;
    @FXML
    private TextField priceField;
    @FXML
    private ChoiceBox<SeatType> seatTypeField;
    @FXML
    private Button addSeatBtn;

    // For the Event Editor
    @FXML
    private VBox sidebar;
    @FXML
    private VBox sidebar2;
    @FXML
    private Button addBtn;
    @FXML
    private Icon refreshIcon;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    //Transition Anchor
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private Label filePathLabel;
    //Table Variables!
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> nameColumn;
    @FXML
    private TableColumn<Event, String> typeColumn;
    @FXML
    private TableColumn<Event, String> cityColunm;
    @FXML
    private TableColumn<Event, String> descriptionColumn;
    @FXML
    private TableColumn<Event, String> addressColumn;
    @FXML
    private TableColumn<Event, LocalDate> dateColumn;
    @FXML
    private TableColumn<Event, String> imageColumn;
    @FXML
    private CheckBox notificationNew;
    private ObservableList<Event> eventList = FXCollections.observableArrayList();
    private boolean isEditMode = false;
    // private boolean isEditMode = false;
    private Event event;
    private Event currentEditingEvent;
    @FXML
    public void initialize() {
        dataUniEvent = UnieventsApplication.getDataUniEvent();
        eventType.getItems().setAll(EventType.values());
        seatTypeField.getItems().setAll(SeatType.values());
        // dataUniEvent = UnieventsApplication.getDataUniEvent();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().eventTypeProperty().asString());
        cityColunm.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        imageColumn.setCellValueFactory(cellData -> cellData.getValue().imageProperty());

        sidebar.setTranslateY(1000);
        sidebar2.setTranslateY(1000);
        refreshTable();
    }
    public void showEventEditor(){
        inTransition(sidebar);
    }
    public void saveInforEventEditor(ActionEvent e) {
        Admin admin = Admin.getInstance();
        if (!validateFields()) return;
        
        if (isEditMode) {
            currentEditingEvent.nameProperty().set(nameField.getText());
            currentEditingEvent.descriptionProperty().set(descriptionField.getText());
            currentEditingEvent.cityProperty().set(cityField.getText());
            currentEditingEvent.eventTypeProperty().set(eventType.getValue());
            currentEditingEvent.imageProperty().set(filePathLabel.getText());
            currentEditingEvent.dateProperty().set(datePicker.getValue());
            currentEditingEvent.addressProperty().set(addressField.getText());
    
            dataUniEvent.updateEvent(currentEditingEvent); 
            isEditMode = false;
        } else {
            event = admin.createEvent(nameField.getText(), descriptionField.getText(), eventType.getValue(), filePathLabel.getText(), datePicker.getValue(), addressField.getText(), cityField.getText()); 
           
        }

        outTransition(sidebar);
        inTransition(sidebar2);
    }
    

    @FXML
    private void handleEdit(ActionEvent event) {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {

            nameField.setText(selectedEvent.getName());
            descriptionField.setText(selectedEvent.getDescription());
            cityField.setText(selectedEvent.getCity());
            eventType.setValue(selectedEvent.getEventType());
            filePathLabel.setText(selectedEvent.getImage());
            datePicker.setValue(selectedEvent.getDate());
            addressField.setText(selectedEvent.getAddress());

            isEditMode = true;
            currentEditingEvent = selectedEvent;


            showEventEditor();
        } else {

            showAlert(AlertType.WARNING, "No Selection", "Please select an event to edit.");
        }
    }


    private boolean validateFields() {
        return (validateField(nameField, "There is something wrong with the Name of the Event") &&
        validateField(descriptionField, "There is something wrong with the Description, so, check it") &&
        validateField(cityField, "There something wrong with the City field, the city can't be just numbers") &&
        validDateEvent() && validateField(addressField, "There is something wrong with the Andress of the Event") &&
        validateField(eventType, "The Event Type is necessary"));
    }
    @FXML
    private void saveSeatData(ActionEvent e){
        // outTransition(sidebar);
        if (!validateSeatFields()) {
            return;
        }
        System.err.println("The event could be null: "+event);
        event.addSeat(Float.parseFloat(priceField.getText()), Short.parseShort(capacityField.getText()), seatTypeField.getValue());
        priceField.setText("");
        seatTypeField.setValue(null);
        capacityField.setText("");

    }
    
    private boolean validateSeatFields() {
        return (validatePriceSeat() && validateCapacitySeat() && validateField(seatTypeField, "The Seat Type must be provided"));
    }


    public void saveAllDataEvent(ActionEvent e){
        outTransition(sidebar2);
        // outTransition(sidebar);
        if (notificationNew.isSelected()) {
            // System.out.println("Notification Acitived");
            dataUniEvent.newEventPromotion(event);
        }
        dataUniEvent.addEvent(event);
        refreshTable();
    }

    private boolean validatePriceSeat() {
        String price = priceField.getText();
        try {
            Integer.parseInt(price);
        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Validation Error", "The Price isn't a numerical value");
            return false;
        }
        return true;
    }
    private boolean validateCapacitySeat() {
        String capa = capacityField.getText();
        try {
            Integer.parseInt(capa);
        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Validation Error", "The Capacity isn't a numerical value!");
            return false;
        }
        return true;
    }
    private boolean validateField(Object field, String errorMessage) {
        boolean isValid = true;
    
        if (field instanceof TextField) {
            TextField textField = (TextField) field;
            String input = textField.getText();
            
            boolean containsLetters = input.matches(".*[a-zA-Z]+.*");
    
            if (input.isEmpty() || !containsLetters) {
                isValid = false;
            }
        } else if (field instanceof ChoiceBox) {
            ChoiceBox<?> choiceBox = (ChoiceBox<?>) field;
            if (choiceBox.getValue() == null) {
                isValid = false;
            }
        }
    
        if (!isValid) {
            showAlert(AlertType.WARNING, "Validation Error", errorMessage);
        }
    
        return isValid;
    }
    
    private boolean validDateEvent() {
        if (datePicker.getValue() != null && datePicker.getValue().isAfter(LocalDate.now().plusWeeks(2))){
            return true;
        }
        else {
            showAlert(AlertType.WARNING, "Validation Error", "The Date must be provided and also the Event needs aleast one week to be created");
        }

        return false;
    }
    
  
    @FXML
    private void saveSource() {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File Path");

        fileChooser.setInitialDirectory(new java.io.File(System.getProperty("user.home")));

        Window primaryStage = filePathLabel.getScene().getWindow();
        java.io.File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // Display the file path or handle it as needed
            filePathLabel.setText("File Path: " + file.getAbsolutePath());
        }
    }
    public void cancelEditor1(ActionEvent e){
        nameField.clear();
        descriptionField.clear();
        cityField.clear();
        eventType.setValue(null);
        filePathLabel.setText("");
        datePicker.setValue(null);
        addressField.clear();
        
        outTransition(sidebar);
    }
    public void cancelEditor2(ActionEvent e){
        nameField.clear();
        descriptionField.clear();
        cityField.clear();
        eventType.setValue(null);
        filePathLabel.setText("");
        datePicker.setValue(null);
        addressField.clear();
        priceField.clear();
        seatTypeField.setValue(null);
        capacityField.clear();

        outTransition(sidebar2);
    }
    //Show methods
    @FXML
    private void inTransition(VBox element) {

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), element);
        transition.setToY(0); 
        element.setVisible(true);
        transition.play();
    }
   
    private void outTransition(VBox element) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), element);
        transition.setToY(-1000); 
        element.setVisible(true);
        transition.play();

  
        transition.setOnFinished(event -> {
            element.setTranslateY(1000);
        });
    }
   
    public void refreshTable() {
        eventList.clear();
        UniEventIterator iterator = dataUniEvent.getEventIterator();
    
        while (iterator.hasNext()) {
            eventList.add((Event) iterator.next());
        }
    
        eventTable.setItems(eventList);
    }
    @FXML
    public void handleDelete(ActionEvent event) {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Event");
            alert.setContentText("Are you sure you want to delete the selected event?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remove the selected event from the data source
                dataUniEvent.removeEvent(selectedEvent);
                eventList.remove(selectedEvent); 
                eventTable.refresh(); 
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Event Selected");
            alert.setContentText("Please select an event to delete.");
            alert.showAndWait();
        }
    }

    public void backHome(ActionEvent event){
        handleSignUpAction("/admin-home.fxml");
    }
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void handleSignUpAction(String path) {
        try {
            // Get the controller and pass the user ID
            transitionScene(anchorRoot, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
}
