package com.uniquindio.finalproject;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

public class Client {
    // private final Collection<Cupon> cupons;
    private int ID;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String mail;
    private String password;
    private final Collection<Purchase> logPurchase;

    public Client(int ID, String name, String lastname, String phoneNumber, String mail, String password){
        this.ID = ID;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.password = password;
        this.logPurchase = new LinkedList<>();
    }
    public void doPurchase(String purchaseID, String Location, float valuePurchase)
    {
        Purchase purchase = new Purchase(purchaseID, Location , valuePurchase);
        //UUID.randomUUID().toString()
        //Has to verify if the Purchuse Exist, this using his ID
        if(!verifyPurchuse(purchase).isPresent()) logPurchase.add(purchase);
    }
    private Optional<Purchase> verifyPurchuse(Purchase purchase){
        Predicate<Purchase> condition = p-> p.getId().equals(purchase.getId());
        return logPurchase.stream().filter(condition).findAny();
    }
   
}