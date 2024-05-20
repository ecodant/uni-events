package com.uniquindio.finalproject.unievents;

import java.io.*;

public class EmailMsgListener implements Serializable {
    private final String email;
    
    public EmailMsgListener(String email){
        this.email = email;
    }
    public void update(){
        //Para la logica
        UnieventsApplication app = UnieventsApplication.getInstance();
    }
}
