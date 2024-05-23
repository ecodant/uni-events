package com.uniquindio.finalproject.unievents;

import java.io.Serializable;

public class EmailMsgListener implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String email;

    public EmailMsgListener(String email) {
        this.email = email;
    }

    public void update(Object obj) {
        UnieventsApplication app = UnieventsApplication.getInstance();
        String subject = "";
        String body = "";

        if (obj instanceof Cupon) {
            Cupon cupon = (Cupon) obj;
            subject = "New Coupon Available!";
            body = "Dear Customer,\n\nWe are excited to inform you about a new coupon!\n" +
                   "Discount Value: " + cupon.getValue() + "%\n\n" +
                   "Thank you for being with us.\n\nBest Regards,\nUniEvents Team";
        } else if (obj instanceof Event) {
            Event event = (Event) obj;
            subject = "New Event Created!";
            body = "Dear Customer,\n\nWe are thrilled to announce a new event!\n" +
                   "Event Name: " + event.getName() + "\n" +
                   "Event Type: " + event.getEventType() + "\n" +
                   "Event Date: " + event.getDate() + "\n\n" +
                   "We hope to see you there!\n\nBest Regards,\nUniEvents Team";
        }

        app.sendMail(email, subject, body);
    }
}
