package com.foodeo.backend.model;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Table(name = "orders")
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="customer_id")
@PrimaryKeyJoinColumn(referencedColumnName="restaurant_id")
@PrimaryKeyJoinColumn(referencedColumnName="driver_id")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Long restaurantId;
    private Long driverId;
//    private LocalTime startTime;
    private boolean delivery;
//    private Time endTime;
@ElementCollection
    private List<Dish> content;
    private double price;
    @Embedded
    private Comment comment;

    public Order() {
    }

    public Order(Long id, Long customerId, Long restaurantId, Long driverId,
                 boolean delivery,
                 List<Dish> content, double price, Comment comment) {
        this.id = id;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.driverId = driverId;
//        this.startTime = startTime;
        this.delivery = delivery;
//        this.endTime = endTime;
        this.content = content;
        this.price = price;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

//    public LocalTime getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(LocalTime startTime) {
//        this.startTime = startTime;
//    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

//    public Time getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Time endTime) {
//        this.endTime = endTime;
//    }

    public List<Dish> getContent() {
        return content;
    }

    public void setContent(List<Dish> content) {
        this.content = content;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }


}
