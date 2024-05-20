package com.uniquindio.finalproject.unievents;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import com.uniquindio.finalproject.unievents.Purchase.PurchaseBuilder;

public class User implements Serializable {
    // private static final long serialVersionUID = 1L;
    private final Collection<Cupon> cupons;
    private int ID;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String mail;
    private String password;
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
        CuponFactory cuponFactory = new CuponFactory();

        cupons.add(cuponFactory.createCupon(CuponType.REGISTERED));
    }
    public void rawPurchase(String seat, float valuePurchase)  
    {   
 
        String purchaseID = UUID.randomUUID().toString();
        PurchaseBuilder builder = new PurchaseBuilder();
        Purchase purchase = builder.idBill(purchaseID).seat(seat).valuePurchase(valuePurchase).build();
        
        // For the future Set an Exception
        if(!verifyPurchuse(purchase).isPresent()) logPurchase.add(purchase); else System.out.println("Can't be possible"); 
        firstCuponValidation();
    }
    public void cuponPurchase(String seat, float valuePurchase, Cupon cupon)
    {
        String purchaseID = UUID.randomUUID().toString();
        PurchaseBuilder builder = new PurchaseBuilder();
        Purchase purchase = builder.idBill(purchaseID).seat(seat).valuePurchase(valuePurchase).cupon(cupon).build();
        
        if(!verifyPurchuse(purchase).isPresent()) logPurchase.add(purchase); else System.out.println("Can't be possible"); 
        firstCuponValidation();
    }
    
    private Optional<Purchase> verifyCupon(Cupon cupon){
        Predicate<Purchase> condition = p-> p.getCupon().equals(cupon);
        return logPurchase.stream().filter(condition).findAny();
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
            cupons.add(cuponFactory.createCupon(CuponType.FIRST_PURCHASE));
        }
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