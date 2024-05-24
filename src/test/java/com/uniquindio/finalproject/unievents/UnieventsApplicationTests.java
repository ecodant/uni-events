package com.uniquindio.finalproject.unievents;
import static org.mockito.Mockito.*;





import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class UnieventsApplicationTests {

    @Mock
    private EmailSenderService emailSenderServiceMock;

    @InjectMocks
    private UnieventsApplication unieventsApplication;

    @Test
    void testSendMail() {
        String mail = "example@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        unieventsApplication.sendMail(mail, subject, body);

        verify(emailSenderServiceMock).sendEmail(mail, subject, body);
    }


}
