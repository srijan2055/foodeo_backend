package com.foodeo.backend.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "dish")
@Entity
public class Dish {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    Long id;


    private String dishName;
    private double price;
    private String imageUrl;
    private Long restaurantId;

    public Dish() {
    }

    public Dish(String dishName, double price, String imageUrl, Long restaurantId) {
        this.dishName = dishName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.restaurantId=restaurantId;
    }

    public Dish( String dishName, double price, String imageUrl) {

        this.dishName = dishName;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dish)) {
            return false;
        }
        Dish dish = (Dish) o;
        return Double.compare(dish.getPrice(), getPrice()) == 0 &&
                getDishName().equals(dish.getDishName()) &&
                getImageUrl().equals(dish.getImageUrl())&&
                getRestaurantId().equals(dish.getRestaurantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDishName(), getPrice(), getImageUrl(),getRestaurantId());
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishName='" + dishName + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
