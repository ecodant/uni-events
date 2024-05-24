package com.uniquindio.finalproject.unievents;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UnieventsApplicationTests {

    @Mock
    private EmailSenderService emailSenderServiceMock;

    @InjectMocks
    private UnieventsApplication unieventsApplication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMail() {
        String mail = "edwin.vinar@uqvirtual.edu.co"; // correo acá
        String subject = "Test mail";
        String body = "Test Body";

        unieventsApplication.sendMail(mail, subject, body);

        verify(emailSenderServiceMock).sendEmail(mail, subject, body);
    }
}
