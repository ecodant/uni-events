package com.uniquindio.finalproject.unievents;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationService implements Serializable {
    // private static final long serialVersionUID = 1L;
    private final List<EmailMsgListener> customers;

    public NotificationService(){
        customers = new ArrayList<>();
    }
    public void subscribe(EmailMsgListener listener){
        customers.add(listener);
    }

    public void unsubscribe(EmailMsgListener listener){
        customers.remove(listener);
    }

    public void notifyClient() {
        customers.forEach(listener -> listener.update());
    }
}
