package com.cegeka.adapters.rest;

public record EmailRequest(String to, String subject, String body) {
    // Empty record body
}
