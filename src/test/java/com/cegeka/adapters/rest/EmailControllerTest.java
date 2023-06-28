package com.cegeka.adapters.rest;

import com.cegeka.adapters.mail.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(EmailController.class)
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSendEmail() throws Exception {
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "This is a test email";

        EmailRequest emailRequest = new EmailRequest(to, subject, body);

        mockMvc.perform(MockMvcRequestBuilders.post("/send-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // You can add additional assertions to verify the behavior or the response if needed
    }
}

