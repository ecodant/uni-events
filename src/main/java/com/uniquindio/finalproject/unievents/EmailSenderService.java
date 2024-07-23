package com.uniquindio.finalproject.unievents;

import java.io.File;
import java.io.IOException;

import com.google.zxing.WriterException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private QRCodeService qrCodeService;

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gridgamesbusiness@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Sent successfully...");
    }

    public void sendEmailWithQRCode(String toEmail, String subject, String body, String qrText) throws IOException, WriterException, MessagingException {
        // Generate QR code file
        String qrFilePath = "qr_code.png";
        File qrFile = qrCodeService.generateQRCodeFile(qrText, qrFilePath);

        // Create a MIME message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);
        helper.addAttachment("QRCode.png", qrFile);

        // Send the email
        mailSender.send(message);
    }
}
