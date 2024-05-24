package com.uniquindio.finalproject.unievents;
import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DataUniEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final NotificationService notificationService;
    private final Collection<Event> events;
    private final Collection<User> users;
    private final Collection<Cupon> cupons;


    public DataUniEvent() {
        notificationService = new NotificationService();
        this.events = new LinkedList<>();
        this.users = new LinkedList<>();
        this.cupons = new LinkedList<>(); // Initialize the cupon collection
        
    }

    public void newEventPromotion(Object obj) {
        notificationService.notifyClient(obj);
    }

    public NotificationService getService() {
        return notificationService;
    }

    public Collection<User> getUsers() {
        return users;
    }
    public Collection<Event> getEvents() {
        return events;
    }
    //Este método nunca debería llarmse para el nuevo contexto
    public void addUser(User user){
        if(!verifyUser(user).isPresent()){
            users.add(user);
        }
        else showAlert(AlertType.INFORMATION, "User registered!", "The user is already registered in the System");
        
    }
    public void addEvent(Event event){
        if(!verifyEvent(event).isPresent()){
            events.add(event);
        }
        // else showAlert(AlertType.INFORMATION, "The Event is already created!", "Please created a new one");
        
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
    private Optional<Event> verifyEvent(Event event){
        Predicate<Event> condition = e-> e.getName().equals(event.getName()) && e.getDescription() == event.getDescription() 
                                    && e.getDate().equals(event.getDate()) &&  e.getEventType().equals(event.getEventType());
        return events.stream().filter(condition).findAny();
    }
    private Optional<Cupon> verifyCupon(Cupon cupon){
        Predicate<Cupon> condition = p-> p.equals(cupon);
        return cupons.stream().filter(condition).findAny();
    }
    public void addCupon(Cupon cupon) {
        if (!verifyCupon(cupon).isPresent()) {
            cupons.add(cupon);
        } else {
            showAlert(AlertType.INFORMATION, "Cupon already exists!", "The cupon is already saved in the system.");
        }
    }
    public void updateEvent(Event updatedEvent) {
        for (Event event : events) {
            if (event.equals(updatedEvent)) {
                event.nameProperty().set(updatedEvent.getName());
                event.descriptionProperty().set(updatedEvent.getDescription());
                event.eventTypeProperty().set(updatedEvent.getEventType());
                event.imageProperty().set(updatedEvent.getImage());
                event.dateProperty().set(updatedEvent.getDate());
                event.addressProperty().set(updatedEvent.getAddress());
                break;
            }
        }
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
    public Optional<User> loginChecker(String password, String mail){
        Predicate<User> condicion = u->  u.getMail().equals(mail) && u.getPassword().equals(password);
        return users.stream().filter(condicion).findAny();
    }
    public UniEventIterator<User> getUserIterator() {
        return new UserIterator(users);
    }
    public UniEventIterator<Cupon> getCuponIterator() {
        return new CuponIterator(cupons);
    }
    public UniEventIterator<Event> getEventIterator() {
        return new EventIterator(events);
    }
    public void removeEvent(Event event) {
        events.remove(event);
    }
    public void removeCupon(Cupon cupon) {
        cupons.remove(cupon);
    }
    
    public static DataUniEvent loadFromFile(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (DataUniEvent) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Collection<Event> filterEvents(String city, String eventName, EventType eventType) {
        if (city == null && eventName == null && eventType == null) {
            showAlert(AlertType.WARNING, "Validation Error", "At least one parameter (city, event name, or event type) must be provided for filtering.");
            return events;
        }

        return events.stream()
                .filter(event -> (event.getCity().equals(city)) || event.getName().equals(eventName) ||
                                 event.getEventType().equals(eventType)).collect(Collectors.toList());
    }

}
