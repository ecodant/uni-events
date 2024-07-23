package com.uniquindio.finalproject.unievents;

import java.io.IOException;
import java.util.Collection;

import java.util.Optional;

import com.google.zxing.WriterException;

import jakarta.mail.MessagingException;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class BuyPanelController extends BaseController {

 
    //Root Bro
    @FXML
    private AnchorPane anchorRoot;
    //Buy Fields
    @FXML
    private TextField buyTicketsNumField;
    @FXML
    private ChoiceBox<SeatType> buySeatTypeField;
    @FXML
    private ChoiceBox<Float> buyCuponField;

    //Filter requieremtns
    @FXML
    private TextField nameEventField;
    @FXML
    private TextField cityField;
    @FXML
    private ChoiceBox<EventType> typeField;
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> nameColumn;
    @FXML
    private TableColumn<Event, EventType> typeColumn;
    @FXML
    private TableColumn<Event, String> cityColumn;
    @FXML
    private TableColumn<Event, String> descriptionColumn;
    @FXML
    private TableColumn<Event, String> addressColumn;
    @FXML
    private TableColumn<Event, String> dateColumn;
    @FXML
    private HBox sidebar;
    UnieventsApplication app;
    private DataUniEvent dataUniEvent;
    private User user;
    private Event selectedEvent;
    private Seat seat;
    @FXML
    public void initialize() {

        dataUniEvent = UnieventsApplication.getDataUniEvent();
        sidebar.setTranslateX(1200);
        
        // cityField.getItems().setAll("Bogotá", "Medellín", "Cali", "Barranquilla", "Cartagena", "Bucaramanga", "Pereira", "Manizales", "Santa Marta", "Villavicencio");
        typeField.getItems().setAll(EventType.FESTIVAL,EventType.CONCERT, EventType.THEATER,
             EventType.SPORT, EventType.OTHER, null);

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        typeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEventType()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));

        refreshTable(dataUniEvent.getEvents());
    }

    @FXML
    private void searchEvents(ActionEvent event) {
        Collection<Event> filteredEvents = dataUniEvent.filterEvents(cityField.getText(), nameEventField.getText(), typeField.getValue());

        refreshTable(filteredEvents);
    }

    private void refreshTable(Collection<Event> events) {
        ObservableList<Event> eventList = FXCollections.observableArrayList(events);
        eventTable.setItems(eventList);
    }

    @FXML
    private void clearFilters(ActionEvent event) {
        nameEventField.clear();
        cityField.clear();
        typeField.setValue(null);
        refreshTable(dataUniEvent.getEvents());
    }
    
    @FXML
    private void backHome(ActionEvent event) {
        handleSignUpAction("/startup-UI.fxml");
    }

    @FXML
    private void buyTicket(ActionEvent event) {
        float price = 0;
    
        if (!validateFields()) return;
    
        Float discountValue = buyCuponField.getValue() != null ? buyCuponField.getValue() : 0.00f;
    
        if (selectedEvent.getAvailableSeat(buySeatTypeField.getValue()).isPresent()) {
            seat = selectedEvent.getAvailableSeat(buySeatTypeField.getValue()).get();
            if ((short) Integer.parseInt(buyTicketsNumField.getText()) <= seat.getCapacity()) {
                price = seat.getPrice() * (short) Integer.parseInt(buyTicketsNumField.getText());
            } else {
                showAlert(AlertType.INFORMATION, "Insufficient tickets available", "The quotas for this seat are not enough.");
                return;
            }
        }
    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Purchase");
        String msg = "Your base price is: " + price + "\n";
        msg += "The discount selected is: " + discountValue + "\n";
        msg += "So the total to be paid is: " + (price - (price * discountValue));
        alert.setContentText(msg);
        Purchase purchase;
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (discountValue == 0.00f) {
                purchase = user.rawPurchase(buySeatTypeField.getValue(), price);
            } else {
                purchase = user.cuponPurchase(buySeatTypeField.getValue(), price, discountValue);
                user.removeCupon(discountValue);
            }
    
            seat.decreaseCapacity((short) Integer.parseInt(buyTicketsNumField.getText()));
            selectedEvent.updateSeat(seat);

            try {
                try {
                    moveToPurchaseComplete(user, purchase);
                } catch (WriterException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean validateFields() {
        return (validateSeatBuy()  && validateTicketsValue());
    }
    public void cancelBuy(ActionEvent e){
        // selectedEvent = null;
        buyTicketsNumField.clear();
        buySeatTypeField.setValue(null);
        buyCuponField.setValue(null);
    
        outTransition(sidebar);
    }
    
    
    @FXML
    private void nextStep(ActionEvent event) {
        System.out.println("Next Step Enter");
        selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            System.out.println("Event Selected: " + selectedEvent.getName());
            buySeatTypeField.setItems(FXCollections.observableArrayList(selectedEvent.getAvailableSeatTypes()));
            buyCuponField.setItems(FXCollections.observableArrayList(user.getAllDiscountValues()));
            inTransition(sidebar);
            System.out.println("Next Step Completed");
        } else {
            System.out.println("No Event Selected");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Event Selected");
            alert.setContentText("Please select an event to delete.");
            alert.showAndWait();
        }
    }
    
    private boolean validateSeatBuy(){
        if (buySeatTypeField.getValue() != null){
            return true;
        }
        else{
            showAlert(AlertType.WARNING, "Seat undefined", "You have to select one seat for the event!");
        }
        return false;
    }

     private void moveToPurchaseComplete(User user, Purchase purchase) throws IOException, WriterException, MessagingException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/purchase-complete.fxml"));
        Parent root = loader.load();
        app = UnieventsApplication.getInstance();
        PurchaseCompleteController controllerP = loader.getController();
        System.out.println("User passed to the next FXML");
        controllerP.setUser(user);
        String msg = "Dear Customer,\n\n" +
            "This email confirms your recent purchase at UniEvents.\n\n" +
            "Purchase Details:\n" +
            "The Location was: " +purchase.getLocation()+"\n" +
            "The total of the purchase was: "+purchase.getValuePurchase() +
            "\n" +
            "Also attached is the QR with purchase ID, so, We hope you enjoy your upcoming event! If you have any questions, please don't hesitate to contact us.\n\n" +
            "Best regards,\n" +
            "UniEvents Team";
        
        app.sendMailWithQRCode(user.getMail(), "Bill Information", msg, purchase.getId());
        Scene scene = anchorRoot.getScene();
        StackPane container = (StackPane) scene.getRoot();

        root.translateXProperty().set(scene.getWidth());
        container.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.8), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            container.getChildren().remove(anchorRoot);
        });
        timeline.play();
    }

    private boolean validateTicketsValue() {
        try {
            Integer.parseInt(buyTicketsNumField.getText());
        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Validation Error", "The number of Tickets has to be a integer");
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
    private void handleSignUpAction(String path) {
        try {
            transitionScene(anchorRoot, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void inTransition(HBox element) {
        // Animate the sidebar to slide in
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), element);
        transition.setToX(0); // Move to the original position
        element.setVisible(true);
        transition.play();
    }
   
    private void outTransition(HBox element) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), element);
        transition.setToX(-1200); // Move off the screen
        element.setVisible(true);
        transition.play();

  
        transition.setOnFinished(event -> {
            element.setTranslateX(1200);
        });
    }
    public void setUser(User user){
        this.user = user;
    }
}
