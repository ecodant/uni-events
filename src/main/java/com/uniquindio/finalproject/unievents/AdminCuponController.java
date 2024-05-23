package com.uniquindio.finalproject.unievents;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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


public class AdminCuponController extends BaseController {

    private DataUniEvent dataUniEvent;
    //Cupon Fields
    @FXML
    private TextField discountField;
    @FXML
    private ChoiceBox<CuponType> cuponType;
    @FXML
    private DatePicker dateCupon;


    // For the Event Editor
    @FXML
    private VBox sidebar;

    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    //Transition Anchor
    @FXML
    private AnchorPane anchorRoot;

    //Table Variables!
    @FXML
    private TableView<Cupon> cuponTable;
    @FXML
    private TableColumn<Cupon, Float> valueColumn;
    @FXML
    private TableColumn<Cupon, String> typeColumn;
    @FXML
    private TableColumn<Cupon, LocalDate> expirationColumn;

    private ObservableList<Cupon> cuponList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        dataUniEvent = UnieventsApplication.getDataUniEvent();

        cuponType.getItems().setAll(CuponType.WEEKEND, CuponType.BLACKFRIDAY, CuponType.SPECIAL);
        valueColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getValue()).asObject());
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
        expirationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getDateOfExpiry()));
        sidebar.setTranslateY(1000);
        refreshTable();
    }

    public void cancelEditor(ActionEvent e){
        outTransition(sidebar);
    }
    public void showCuponEditor(){
        inTransition(sidebar);
    }

    public void saveCupon(ActionEvent event) throws NumberFormatException, NoSuchFieldException {
        
        if(!validateFields()) return;
        Admin admin = Admin.getInstance();
        Cupon cupon = admin.createCupon(cuponType.getValue(), Float.parseFloat(discountField.getText()), dateCupon.getValue());
        //Cupones en circulación
        dataUniEvent.addCupon(cupon);
        dataUniEvent.newEventPromotion(cupon);
        refreshTable();

        outTransition(sidebar);
    }
    // validateFields()
    
    private boolean validateFields() {
        return (validateField(discountField, "Check the discount Field, there something wrong") &&
        validateField(cuponType, "The Cupon Type must be provided") &&
        validateField(dateCupon, "The date is necessary"));
       
    }
    private boolean validateField(Object field, String errorMessage) {
        boolean isValid = true;
    
        if (field instanceof DatePicker) {
            DatePicker datePicker = (DatePicker) field;
            if (datePicker.getValue() == null && !(datePicker.getValue().isAfter(LocalDate.now().plusWeeks(7)))) {
                isValid = false;
            }
        } else if (field instanceof ChoiceBox) {
            ChoiceBox<?> choiceBox = (ChoiceBox<?>) field;
            if (choiceBox.getValue() == null) {
                isValid = false;
            }
        }else if (field instanceof TextField) {
            TextField textField = (TextField) field;
            if (textField.equals(discountField)) {
                String text = textField.getText().trim(); // Trim leading and trailing spaces
                if (text.isEmpty()) {
                    isValid = false;
                } else {
                    try {
                        float discountValue = Float.parseFloat(text);
                        if (discountValue <= 0 || discountValue >= 1) {
                            isValid = false;
                        }
                    } catch (NumberFormatException e) {
                        isValid = false;
                    }
                }
            }
        }
    
        if (!isValid) {
            showAlert(AlertType.WARNING, "Validation Error", errorMessage);
        }
    
        return isValid;
    }

    
    @FXML
    public void handleDelete(ActionEvent event) {
        Cupon selectedCupon = cuponTable.getSelectionModel().getSelectedItem();
        if (selectedCupon != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete Cupon");
            alert.setContentText("Are you sure you want to delete this cupon?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                dataUniEvent.removeCupon(selectedCupon);
                refreshTable();
            }
        } else {
            showAlert(AlertType.WARNING, "No Cupon Selected", "Please select a cupon to delete.");
        }
    }
    public void refreshTable() {
        cuponList.clear();
        UniEventIterator iterator = dataUniEvent.getCuponIterator();
    
        while (iterator.hasNext()) {
            cuponList.add((Cupon) iterator.next());
        }
    
        cuponTable.setItems(cuponList);
    }
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
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void backHome(ActionEvent event){
        handleSignUpAction("/admin-home.fxml");
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
