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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import com.gluonhq.charm.glisten.control.Icon;

// import ch.qos.logback.core.util.Duration;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

// import javafx.animation.TranslateTransition;


public class AdminPanelController extends BaseController {
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
    private TableColumn<Event, String> descriptionColumn;
    @FXML
    private TableColumn<Event, String> addressColumn;
    @FXML
    private TableColumn<Event, LocalDate> dateColumn;
    @FXML
    private TableColumn<Event, String> imageColumn;
    private Event event;
    private ObservableList<Event> eventList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        dataUniEvent = UnieventsApplication.getDataUniEvent();
        eventType.getItems().setAll(EventType.values());
        seatTypeField.getItems().setAll(SeatType.values());
        // dataUniEvent = UnieventsApplication.getDataUniEvent();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().eventTypeProperty().asString());
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
    public void saveInforEventEditor(ActionEvent e){
        Admin admin = Admin.getInstance();
        if(!validateFields()) return;
        System.out.println("Yeah all is good");
        outTransition(sidebar);
        event = admin.createEvent(nameField.getText(), descriptionField.getText(), eventType.getValue(), filePathLabel.getText(), datePicker.getValue(), addressField.getText());
        inTransition(sidebar2);

    }
    private boolean validateFields() {
        return (validateField(nameField, "There is something wrong with the Name of the Event") &&
        validateField(descriptionField, "There is something wrong with the Description, so, check it") &&
        validateField(addressField, "There is something wrong with the Andress of the Event") &&
        validateField(datePicker, "The Date must be provided") &&
        validateField(eventType, "The Event Type is necessary"));
    }
    @FXML
    private void saveSeatData(ActionEvent e){
        // outTransition(sidebar);
        if (!validateSeatFields()) {
            return;
        }

        event.addSeat(Float.parseFloat(priceField.getText()), Short.parseShort(capacityField.getText()), seatTypeField.getValue());
        // dataUniEvent.addEvent(event);
        // outTransition(sidebar2);
        showSeatAddedAnimation();
    }

    private boolean validateSeatFields() {
        return (validatePriceSeat() && validateCapacitySeat() && validateField(seatTypeField, "The Seat Type must be provided"));
    }

    private void showSeatAddedAnimation() {
        // Create a text node for "Seat Added"
        Text seatAddedText = new Text("Seat Added");
        seatAddedText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #000000;");

        // Add the text node to your layout
        // For example, if you have a VBox named 'root':
        anchorRoot.getChildren().add(seatAddedText);

        // Set initial properties
        seatAddedText.setOpacity(0);
        seatAddedText.setTranslateY(-100);

        // Create TranslateTransition for Y translation
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), seatAddedText);
        translateTransition.setToY(0);

        // Create FadeTransition for opacity animation
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), seatAddedText);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        // Play both transitions
        translateTransition.play();
        fadeTransition.play();

        // Remove the text node after the animation finishes
        fadeTransition.setOnFinished(event -> {
            anchorRoot.getChildren().remove(seatAddedText);
        });
    }

    public void saveAllDataEvent(ActionEvent e){
        outTransition(sidebar2);
        // outTransition(sidebar);
    
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
            
            // Check if the input contains both letters and numbers
            boolean containsLetters = input.matches(".*[a-zA-Z]+.*");
            // boolean containsNumbers = input.matches(".*\\d+.*");
    
            if (input.isEmpty() || !containsLetters) {
                isValid = false;
            }
        } else if (field instanceof DatePicker) {
            DatePicker datePicker = (DatePicker) field;
            if (datePicker.getValue() == null) {
                isValid = false;
            }
        }
        else if (field instanceof ChoiceBox) {
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
  
    @FXML
    private void saveSource() {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File Path");

        // Set the initial directory (optional)
        fileChooser.setInitialDirectory(new java.io.File(System.getProperty("user.home")));

        // Open the file chooser dialog
        Window primaryStage = filePathLabel.getScene().getWindow();
        java.io.File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // Display the file path or handle it as needed
            filePathLabel.setText("File Path: " + file.getAbsolutePath());
        }
    }
    public void cancelEditor1(ActionEvent e){
        outTransition(sidebar);
    }
    public void cancelEditor2(ActionEvent e){
        outTransition(sidebar2);
    }
    //Show methods
    @FXML
    private void inTransition(VBox element) {
        // Animate the sidebar to slide in
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), element);
        transition.setToY(0); // Move to the original position
        element.setVisible(true);
        transition.play();
    }
   
    private void outTransition(VBox element) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), element);
        transition.setToY(-1000); // Move off the screen
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
            // Confirmation dialog before deleting
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Event");
            alert.setContentText("Are you sure you want to delete the selected event?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remove the selected event from the data source
                dataUniEvent.removeEvent(selectedEvent);
                eventList.remove(selectedEvent); // Remove from the observable list
                eventTable.refresh(); // Refresh the table view
            }
        } else {
            // If no item is selected, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Event Selected");
            alert.setContentText("Please select an event to delete.");
            alert.showAndWait();
        }
    }

    public void backHome(ActionEvent event){
        handleSignUpAction("/startup-UI.fxml");
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
