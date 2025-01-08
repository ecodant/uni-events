package com.uniquindio.finalproject.unievents;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.uniquindio.finalproject.unievents.Purchase.PurchaseBuilder;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Collection<Cupon> cupons;
    private int ID;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String mail;
    private String password;
    // UnieventsApplication app;
    private final Collection<Purchase> logPurchase;

    public User(int ID, String name, String lastname, String phoneNumber, String mail, String password){
        this.ID = ID;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.password = password;
        this.logPurchase = new LinkedList<>();
        this.cupons = new LinkedList<>();
        //Primer cupon de 15% por registro
        // app = UnieventsApplication.getInstance();
        CuponFactory cuponFactory = new CuponFactory();
        cupons.add(cuponFactory.createCupon(CuponType.REGISTERED , (float) 0.15, LocalDate.now().plusDays(30)));
   
    }
    public Purchase rawPurchase(SeatType seat, float valuePurchase)  
    {   
 
        String purchaseID = UUID.randomUUID().toString();
        PurchaseBuilder builder = new PurchaseBuilder();
        
        // For the future Set an Exception
        firstCuponValidation();
        return builder.idBill(purchaseID).seat(seat).valuePurchase(valuePurchase).build();
    }
    public Purchase cuponPurchase(SeatType seat, float valuePurchase, float cuponValue)
    {
        String purchaseID = UUID.randomUUID().toString();
        PurchaseBuilder builder = new PurchaseBuilder();
        firstCuponValidation();

        return builder.idBill(purchaseID).seat(seat).valuePurchase(valuePurchase  - (valuePurchase * cuponValue)).build();
        
        
    }
    public void addPurchase(Purchase purchase){
        logPurchase.add(purchase);
    }
    public Optional<Cupon> searchCupon(float discountValue, LocalDate dateOfExpiry) {
        Predicate<Cupon> condition = c -> c.getValue() == discountValue && c.getDateOfExpiry().equals(dateOfExpiry);
        return cupons.stream().filter(condition).findAny();
    }

    private Optional<Purchase> verifyPurchuse(Purchase purchase){
        Predicate<Purchase> condition = p-> p.getId().equals(purchase.getId());
        return logPurchase.stream().filter(condition).findAny();
    }
    //Validación Primer Cupon
    public void firstCuponValidation() {
        Predicate<Cupon> condicion = c-> c.getClass().equals(CuponFirstPurchase.class);
        boolean cuponValidation = cupons.stream().filter(condicion).findAny().isPresent();
        if(!cuponValidation && cupons.size() <= 1) {
            CuponFactory cuponFactory = new CuponFactory();
        
            cupons.add(cuponFactory.createCupon(CuponType.FIRST_PURCHASE , (float) 0.15, LocalDate.now().plusDays(30)));
        }
    }
    public List<Float> getAllDiscountValues() {
        return cupons.stream()
                     .map(Cupon::getValue)
                     .collect(Collectors.toList());
    }
    
    public void removeCupon(float discountValue) {
        Optional<Cupon> cuponToRemove = cupons.stream()
                                              .filter(c -> c.getValue() == discountValue)
                                              .findFirst();
        cuponToRemove.ifPresent(cupons::remove);
    }

    public Collection<Purchase> getLogPurchase() {
        return logPurchase;
    }
    public Collection<Cupon> getCupons() {
        return cupons;
    }
    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getLastname() {
        return lastname;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getMail() {
        return mail;
    }
    public String getPassword() {
        return password;
    }

}