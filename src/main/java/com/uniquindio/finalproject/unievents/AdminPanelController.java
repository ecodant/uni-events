package com.uniquindio.finalproject.unievents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class AdminPanelController extends BaseController {
    private DataUniEvent dataUniEvent;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    @FXML
    private AnchorPane anchorRoot;
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

    private ObservableList<Event> eventList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        dataUniEvent = UnieventsApplication.getDataUniEvent();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().eventTypeProperty().asString());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        imageColumn.setCellValueFactory(cellData -> cellData.getValue().imageProperty());
    
        refreshTable();
    }
    
    @FXML
    public void handleNew(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/eventEditor.fxml"));
            DialogPane eventDialogPane = loader.load();
            EventEditorController eventController = loader.getController();

            Dialog<ButtonType> eventDialog = new Dialog<>();
            eventDialog.setDialogPane(eventDialogPane);
            eventDialog.setTitle("Add New Event");
            eventDialog.initModality(Modality.APPLICATION_MODAL);
            eventDialog.initOwner(anchorRoot.getScene().getWindow());

            Optional<ButtonType> result = eventDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.NEXT) {

                FXMLLoader seatsLoader = new FXMLLoader();
                seatsLoader.setLocation(getClass().getResource("/seatsEditor.fxml"));
                DialogPane seatsDialogPane = seatsLoader.load();
                SeatsEditorController seatsController = seatsLoader.getController();

                seatsController.setNameEvent(eventController.getNameField().getText());
                seatsController.setDescriptionEvent(eventController.getDescriptionField().getText());
                seatsController.setAddresEvent(eventController.getAddressField().getText());
                seatsController.setImgURL(eventController.getImgURL().getText());
                seatsController.setEventType(eventController.getEventType().getValue());
                seatsController.setDateEvent(eventController.getDatePicker().getValue());

                Dialog<ButtonType> seatsDialog = new Dialog<>();
                seatsDialog.setDialogPane(seatsDialogPane);
                seatsDialog.setTitle("Set Up Seats!");
                seatsDialog.initModality(Modality.APPLICATION_MODAL);
                seatsDialog.initOwner(anchorRoot.getScene().getWindow());
                System.out.println(seatsDialogPane);
                Optional<ButtonType> seatsResult = seatsDialog.showAndWait();
                if (seatsResult.isPresent() && seatsResult.get() == ButtonType.OK) {
                    seatsController.saveAllInfoEvent();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.WARNING, "The Dialog Fails", "Check the Dialog, bro!");
        }
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
    public void insertInTable(){
        dataUniEvent = UnieventsApplication.getDataUniEvent();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().eventTypeProperty().asString());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        imageColumn.setCellValueFactory(cellData -> cellData.getValue().imageProperty());

        UniEventIterator iterator = dataUniEvent.getEventIterator();

        while(iterator.hasNext()){
            eventList.add((Event) iterator.next());
        }

        eventTable.setItems(eventList);
  }
}
