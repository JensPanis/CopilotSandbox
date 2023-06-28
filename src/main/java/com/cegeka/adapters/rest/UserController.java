package com.cegeka.adapters.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@RestController
public class UserController {

    @PostMapping("/generate-xml")
    public ResponseEntity<String> generateXml(@RequestBody User user) {
            // Create XML using the user data
            System.out.println("test1");
            String xml = createXml(user);

            // Set the Content-Type header to "application/xml"
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);

            // Return the XML as the response
            return new ResponseEntity<>(xml, headers, HttpStatus.OK);
    }

    private String createXml(User user) {
        // Create XML using a library like JAXB or any other XML serialization method
        // Here's a simple example using JAXB
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(user, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            // Handle JAXB exception
            throw new RuntimeException("Error creating XML", e);
        }
    }
}
