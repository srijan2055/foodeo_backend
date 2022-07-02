package com.foodeo.backend.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Entity
public class Driver extends User {

    public Driver() {
        this.setType("driver");
    }

    public Driver(String userName, String password, String phoneNumber, String address) {
        super(userName, password, phoneNumber, address);
        this.setType("driver");
    }
}
