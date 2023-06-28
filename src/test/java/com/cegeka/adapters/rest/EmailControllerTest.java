package com.cegeka.adapters.rest;

import com.cegeka.adapters.mail.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailController.class)
public class EmailControllerTest {

    @MockBean
    private EmailService emailService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSendEmail() throws Exception {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Text";

        mockMvc.perform(MockMvcRequestBuilders.post("/send-email")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("to", to)
                        .param("subject", subject)
                        .param("text", text))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(emailService, times(1)).sendEmail(to, subject, text);
    }

    @Test
    public void testSendEmailBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/send-email")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("to", "to")
                        .param("subject", "subject")
                        .param("description", "text")) // description instead of text
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(emailService, times(0)).sendEmail("to", "subject", "text");
    }
}



