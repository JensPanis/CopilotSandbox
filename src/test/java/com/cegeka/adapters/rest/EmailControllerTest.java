package com.cegeka.adapters.rest;

import com.cegeka.adapters.mail.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
@RunWith(MockitoJUnitRunner.class)
public class EmailControllerTest {

    @Mock
    private JavaMailSender javaMailSender;

    private EmailService emailService;

    private EmailController emailController;

    @Before
    public void setUp() {
        emailService = new EmailService(javaMailSender);
        emailController = new EmailController(emailService);
    }

    @Test
    public void testSendEmail() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Text";

        emailController.sendEmail(to, subject, text);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }
}