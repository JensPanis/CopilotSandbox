package com.cegeka.adapters.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
    private String name;
    private int age;

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "age")
    public int getAge() {
        return age;
    }

}
