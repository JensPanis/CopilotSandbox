package com.cegeka.adapters.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XMLController {

    @GetMapping("/generate-xml")
    public ResponseEntity<String> generateXml(@RequestParam String name, @RequestParam int age, @RequestParam String email) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<user>\n" +
                "    <name>" + name + "</name>\n" +
                "    <age>" + age + "</age>\n" +
                "    <email>" + email + "</email>\n" +
                "</user>";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        return new ResponseEntity<>(xml, headers, HttpStatus.OK);
    }
}
