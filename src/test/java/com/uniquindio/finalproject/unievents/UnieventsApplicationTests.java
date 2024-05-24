package com.uniquindio.finalproject.unievents;
import static org.mockito.Mockito.*;

import java.io.IOException;



import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.zxing.WriterException;

@SpringBootTest
class UnieventsApplicationTests {

    @Mock
    private EmailSenderService emailSenderServiceMock;

    @InjectMocks
    private UnieventsApplication unieventsApplication;

    @Test
    void testSendMail() {
        // Setup
        String mail = "example@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        unieventsApplication.sendMail(mail, subject, body);

        verify(emailSenderServiceMock).sendEmail(mail, subject, body);
    }


}
