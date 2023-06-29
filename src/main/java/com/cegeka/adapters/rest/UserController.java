package com.cegeka.adapters.rest;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@RestController
public class UserController {

    private final PebbleEngine pebbleEngine;

    @Autowired
    public UserController(PebbleEngine pebbleEngine) {
        this.pebbleEngine = pebbleEngine;
    }


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


    @PostMapping("/generate-xml-with-pebble")
    public String generateXmlWithPebble(@RequestBody User user) throws IOException, SAXException {
        // Render the Pebble template with the user data
        PebbleTemplate template = pebbleEngine.getTemplate("user.xml");
        StringWriter writer = new StringWriter();
        template.evaluate(writer, Map.of("user", user));


        String xml = writer.toString();
        validate(xml);



        return xml;
    }

    private void validate(String xml) throws SAXException, IOException {
        File xsdFile = new File("templates/user.xsd");
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsdFile);

        // Create a validator from the schema
        Validator validator = schema.newValidator();

        Source xmlSource = new StreamSource(xml);

        // Validate the XML against the schema
        validator.validate(xmlSource);
    }
}
