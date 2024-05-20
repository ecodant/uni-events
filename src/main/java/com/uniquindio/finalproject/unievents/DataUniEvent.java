package com.uniquindio.finalproject.unievents;
import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DataUniEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final NotificationService notificationService;
    private final Collection<Event> events;
    private final Collection<City> cities;
    private final Collection<User> users;
    public DataUniEvent() {
        notificationService = new NotificationService();
        this.events = new LinkedList<>();
        this.cities = new LinkedList<>();
        this.users = new LinkedList<>();
    }

    public void newEventPromotion() {
        notificationService.notifyClient();
    }

    public NotificationService getService() {
        return notificationService;
    }

    public Collection<City> getCities() {
        return cities;
    }
    public void addUser(User user){
        if(!verifyUser(user).isPresent()){
            users.add(user);
        }
        else showAlert(AlertType.INFORMATION, "User registered!", "The user is already registered in the System");
        
    }
     private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private Optional<User> verifyUser(User user){
        Predicate<User> condition = u-> u.getName().equals(user.getName()) && u.getID() == user.getID() && u.getMail() == user.getMail();
        return users.stream().filter(condition).findAny();
    }
    // Initialize main cities
    public void initializeMainCities() {
        cities.add(new City("Bogotá"));
        cities.add(new City("Medellín"));
        cities.add(new City("Cali"));
        cities.add(new City("Barranquilla"));
        cities.add(new City("Cartagena"));
        cities.add(new City("Bucaramanga"));
        cities.add(new City("Pereira"));
        cities.add(new City("Manizales"));
        cities.add(new City("Santa Marta"));
        cities.add(new City("Villavicencio"));
    }

    // Serialization method
    public void saveToFile(String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(this);
            System.out.println("DataUniEvent serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Optional<User> searchUser(User user){
        Predicate<User> condicion = u-> u.getName().equals(user.getName()) && u.getMail().equals(user.getMail()) && u.getID() == user.getID();
        return users.stream().filter(condicion).findAny();
    }
    // Deserialization method
    public static DataUniEvent loadFromFile(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (DataUniEvent) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
