package com.uniquindio.finalproject.unievents;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;

public class SeatsEditorController {

    private DataUniEvent dataUniEvent;
    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField seatsConcert;
    @FXML
    private TextField seatsArena;
    @FXML
    private TextField seatsBox;
    @FXML
    private TextField seatsVIP;
    @FXML
    private TextField seatsLawn;
    @FXML
    private TextField seatsGeneral;
    @FXML
    private TextField seatsBleacher;
    @FXML
    private TextField priceConcert;
    @FXML
    private TextField priceArena;
    @FXML
    private TextField priceBleacher;
    @FXML
    private TextField priceBox;
    @FXML
    private TextField priceGeneral;
    @FXML
    private TextField priceVIP;
    @FXML
    private TextField priceLawn;
    @FXML
    private TextField arenaCapacity;
    @FXML
    private TextField boxCapacity;
    @FXML
    private TextField vipCapacity;
    @FXML
    private TextField lawnCapacity;
    @FXML
    private TextField generalCapacity;
    @FXML
    private TextField bleacherCapacity;
    @FXML
    private TextField concertCapacity;

    short seatsConcertValue = 0;
    short seatsArenaValue = 0;
    short seatsBoxValue = 0;
    short seatsVIPValue = 0;
    short seatsLawnValue = 0;
    short seatsGeneralValue = 0;
    short seatsBleacherValue = 0;

    float priceConcertValue = 0;
    float priceArenaValue = 0;
    float priceBleacherValue = 0;
    float priceBoxValue = 0;
    float priceGeneralValue = 0;
    float priceVIPValue = 0;
    float priceLawnValue = 0;

    short arenaCapacityValue = 0;
    short boxCapacityValue = 0;
    short vipCapacityValue = 0;
    short lawnCapacityValue = 0;
    short generalCapacityValue = 0;
    short bleacherCapacityValue = 0;
    short concertCapacityValue = 0;
    //Values for a Event that came from the controller before
    private String nameEvent;

    private String descriptionEvent;

    private LocalDate dateEvent;

    private String addresEvent;

    private String imgURL;

    private  EventType eventType;

    private Collection<Seat> seatCollection;
    @FXML
    public void initialize() {
        // dialogPane.lookupButton(ButtonType.OK).addEventFilter(
        //     javafx.event.ActionEvent.ACTION, event -> saveAllInfoEvent()
        // );
        // dialogPane.lookupButton(ButtonType.CANCEL).addEventFilter(
        //     javafx.event.ActionEvent.ACTION, event -> handleCancel()
        // );
    }

    public void saveAllInfoEvent() {
        dataUniEvent = UnieventsApplication.getDataUniEvent();
        Admin admin = Admin.getInstance();
        convertTextFieldValues();
        seatCollection = new LinkedList<>();
        generateSeats(priceConcertValue, concertCapacityValue, SeatType.CONCERT, seatsConcertValue);
        generateSeats(priceArenaValue, arenaCapacityValue, SeatType.ARENA, seatsArenaValue);
        generateSeats(priceBleacherValue, bleacherCapacityValue, SeatType.BLEACHER, seatsBleacherValue);
        generateSeats(priceBoxValue, boxCapacityValue, SeatType.BOX, seatsBoxValue);
        generateSeats(priceGeneralValue, generalCapacityValue, SeatType.GENERAL_ADMISSION, seatsGeneralValue);
        generateSeats(priceVIPValue, vipCapacityValue, SeatType.VIP, seatsVIPValue);
        generateSeats(priceLawnValue, lawnCapacityValue, SeatType.LAWN, seatsLawnValue);
    
        Event event = admin.createEvent(nameEvent, descriptionEvent, eventType, imgURL, dateEvent, addresEvent, seatCollection);
        dataUniEvent.addEvent(event);
    
        // Load the Admin Panel and refresh the table
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin-panel.fxml"));
            AnchorPane adminPane = loader.load();
            AdminPanelController adminPanelController = loader.getController();
            adminPanelController.refreshTable();  // Refresh the table with the new event
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        dialogPane.getScene().getWindow().hide();
    }
    

    private void generateSeats(float price, short capacity, SeatType seatType, short numberOfSeats) {
        for (int i = 0; i < numberOfSeats; i++) {
            Seat seat = new Seat(price, capacity, seatType);
            seatCollection.add(seat);
        }
    }

    public void handleCancel() {
        dialogPane.getScene().getWindow().hide();
    }

    public void convertTextFieldValues() {
        try {
            // Check if the text fields are not empty before parsing
            if (!seatsConcert.getText().isEmpty()) {
                seatsConcertValue = Short.parseShort(seatsConcert.getText());
            }
            if (!seatsArena.getText().isEmpty()) {
                seatsArenaValue = Short.parseShort(seatsArena.getText());
            }
            if (!seatsBox.getText().isEmpty()) {
                seatsBoxValue = Short.parseShort(seatsBox.getText());
            }
            if (!seatsVIP.getText().isEmpty()) {
                seatsVIPValue = Short.parseShort(seatsVIP.getText());
            }
            if (!seatsLawn.getText().isEmpty()) {
                seatsLawnValue = Short.parseShort(seatsLawn.getText());
            }
            if (!seatsGeneral.getText().isEmpty()) {
                seatsGeneralValue = Short.parseShort(seatsGeneral.getText());
            }
            if (!seatsBleacher.getText().isEmpty()) {
                seatsBleacherValue = Short.parseShort(seatsBleacher.getText());
            }
    
            if (!priceConcert.getText().isEmpty()) {
                priceConcertValue = Float.parseFloat(priceConcert.getText());
            }
            if (!priceArena.getText().isEmpty()) {
                priceArenaValue = Float.parseFloat(priceArena.getText());
            }
            if (!priceBleacher.getText().isEmpty()) {
                priceBleacherValue = Float.parseFloat(priceBleacher.getText());
            }
            if (!priceBox.getText().isEmpty()) {
                priceBoxValue = Float.parseFloat(priceBox.getText());
            }
            if (!priceGeneral.getText().isEmpty()) {
                priceGeneralValue = Float.parseFloat(priceGeneral.getText());
            }
            if (!priceVIP.getText().isEmpty()) {
                priceVIPValue = Float.parseFloat(priceVIP.getText());
            }
            if (!priceLawn.getText().isEmpty()) {
                priceLawnValue = Float.parseFloat(priceLawn.getText());
            }
    
            if (!arenaCapacity.getText().isEmpty()) {
                arenaCapacityValue = Short.parseShort(arenaCapacity.getText());
            }
            if (!boxCapacity.getText().isEmpty()) {
                boxCapacityValue = Short.parseShort(boxCapacity.getText());
            }
            if (!vipCapacity.getText().isEmpty()) {
                vipCapacityValue = Short.parseShort(vipCapacity.getText());
            }
            if (!lawnCapacity.getText().isEmpty()) {
                lawnCapacityValue = Short.parseShort(lawnCapacity.getText());
            }
            if (!generalCapacity.getText().isEmpty()) {
                generalCapacityValue = Short.parseShort(generalCapacity.getText());
            }
            if (!bleacherCapacity.getText().isEmpty()) {
                bleacherCapacityValue = Short.parseShort(bleacherCapacity.getText());
            }
            if (!concertCapacity.getText().isEmpty()) {
                concertCapacityValue = Short.parseShort(concertCapacity.getText());
            }
    
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
    


    public void setNameEvent(String nameEvent) { this.nameEvent = nameEvent; }
    public void setDescriptionEvent(String descriptionEvent) { this.descriptionEvent = descriptionEvent; }
    public void setDateEvent(LocalDate dateEvent) { this.dateEvent = dateEvent; }
    public void setAddresEvent(String addresEvent) { this.addresEvent = addresEvent; }
    public void setImgURL(String imgURL) { this.imgURL = imgURL; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }
}