package com.foodeo.backend.model;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@Entity
public class Customer extends User {

    public Customer() {
        this.setType("customer");
    }

    public Customer(String userName, String password, String phoneNumber, String address) {
        super(userName, password, phoneNumber, address);
        this.setType("customer");
    }
}
