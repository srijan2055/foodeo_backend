package com.foodeo.backend.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="restaurant")

public class Restaurant extends User {




    @ElementCollection(targetClass =Dish.class)
    @Transient
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Dish> menu= new ArrayList<>();
    @Embedded
    @Lob
    private RestaurantInfo information;



    public Restaurant() {
        this.setType("restaurant");
    }

    public Restaurant(String userName, String password, String phoneNumber, String address,
                       RestaurantInfo information,
                      List<Dish> menu) {
        super(userName, password, phoneNumber, address);
        this.setType("restaurant");
        this.information = information;
        this.menu = menu;
    }

    public Restaurant(String userName, String password, String phoneNumber, String address) {
        super(userName, password, phoneNumber, address);
        this.setType("restaurant");
    }

    public RestaurantInfo getInformation() {
        return information;
    }

    public void setInformation(RestaurantInfo information) {
        this.information = information;
    }

    @ElementCollection(targetClass =Dish.class)
    public List<Dish> getMenu() {
        return menu;
    }

    @ElementCollection(targetClass =Dish.class)

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "information=" + information +
                ", menu=" + menu +
                '}';
    }
}
