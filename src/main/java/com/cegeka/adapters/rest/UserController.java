package com.cegeka.adapters.rest;

import com.cegeka.adapters.domain.User;
import com.cegeka.adapters.mail.UserService;
import jakarta.annotation.Resource;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ByteArrayResource> getUserXml(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // generate the XML file
        String xml = generateXml(user);

        // create a ByteArrayResource from the XML string
        ByteArrayResource resource = new ByteArrayResource(xml.getBytes());

        // set the headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=user.xml");

        // return the response entity with the XML file as the body
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(xml.getBytes().length)
                .contentType(MediaType.APPLICATION_XML)
                .body(resource);
    }

    private String generateXml(User user) {
        // use a library like JAXB or XStream to generate the XML file
        // here's an example using JAXB:

        try {
            JAXBContext context = JAXBContext.newInstance(User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();
            marshaller.marshal(user, writer);

            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}