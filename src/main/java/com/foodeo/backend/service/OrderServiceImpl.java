package com.foodeo.backend.service;


import com.foodeo.backend.model.Comment;
import com.foodeo.backend.model.Dish;
import com.foodeo.backend.model.Order;
import com.foodeo.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service()
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public int addOrderToCart(Long customerId, Long restaurantId, List<Dish> content) {
        List<Order> currentCart = this.customerCart(customerId);
        for (Order o : currentCart) {
            if (o.getRestaurantId().equals(restaurantId)) {
                List<Dish> menu = o.getContent();
                menu.addAll(content);
                double price = 0;
                for (Dish dish : menu) {
                    price += dish.getPrice();
                }
                o.setPrice(price);
                orderRepository.save(o);
                System.out.println("Order added to the cart");
                return 1;
            }
        }
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setRestaurantId(restaurantId);
        order.setContent(content);
        double price = 0;
        for (Dish dish : content) {
            price += dish.getPrice();
        }
        order.setPrice(price);
        orderRepository.save(order);
        System.out.println("Order added to the cart");
        return 1;
    }

    @Override
    public int checkoutOrder(Long id) {
        Optional<Order> order = getOrder(id);
        if (order.isEmpty()) {
            System.out.println("Order doesn't exist");
            return 0;
        }
//        if (order.get().getStartTime() == null) {
//            order.get().setStartTime(LocalTime.now());
            orderRepository.save(order.get());
            System.out.println("Order checkouts");
            return 1;
//        }
//        System.out.println("Order already checkout");
//        return -1;
    }

    @Override
    public int checkoutAll(List<Order> orders) {
        for (Order order : orders) {
            Long id = order.getId();
            int res = checkoutOrder(id);
            if (res == 0) {
                return 0;
            }
            if (res == -1) {
                return -1;
            }
        }
        return 1;
    }

    @Override
    public int cancelOrder(Long id) {
        Optional<Order> order = getOrder(id);
        if (order.isEmpty()) {
            System.out.println("Order doesn't exist");
            return 0;
        }
        if (!order.get().isDelivery()) {
            orderRepository.deleteById(id);
            System.out.println("Order canceled");
            return 1;
        }
        System.out.println("Can't cancel order. It is either in delivery or finished");
        return -1;
    }

    @Override
    public Optional<Order> getOrder(Long id) {
        if (id != null) {
            return orderRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public int acceptOrder(Long id, Long driverId) {
        Optional<Order> orderOrNot = getOrder(id);
        if (orderOrNot.isPresent()) {
            Order order = orderOrNot.get();
            if ( !order.isDelivery()) {
                order.setDelivery(true);
                order.setDriverId(driverId);
                orderRepository.save(order);
                System.out.println("Driver accepts the order");
                return 1;
            } else {
                System.out.println("The order can't be accepted");
                return 0;
            }
        }
        System.out.println("The order doesn't exist");
        return -1;
    }

    @Override
    public int finishOrder(Long id) {
        Optional<Order> orderOrNot = getOrder(id);
        if (orderOrNot.isPresent()) {
            Order order = orderOrNot.get();
            if (order.isDelivery() ) {
//                order.setEndTime(Time.valueOf(LocalTime.now()));
                orderRepository.save(order);
                System.out.println("Driver finishes the order");
                return 1;
            } else {
                System.out.println("The order can't be finished");
                return 0;
            }
        }
        System.out.println("The order can't be finished");
        return -1;
    }

    @Override
    public List<Order> customerCart(Long customerId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getCustomerId().equals(customerId) )
                .collect(
                        Collectors.toList());
    }

    @Override
    public List<Order> customerGetActiveOrders(Long customerId) {
        return orderRepository.findAll().stream().filter(
                order -> order.getCustomerId().equals(customerId) ).collect(
                Collectors.toList());
    }

    @Override
    public List<Order> customerFindPastOrders(Long customerId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getCustomerId().equals(customerId) )
                .collect(
                        Collectors.toList());
    }

    @Override
    public List<Order> getAllPendingOrders() {
        return orderRepository.findAll().stream()
                .filter(order ->  !order.isDelivery()).collect(
                        Collectors.toList());
    }

    @Override
    public Order driverGetActiveOrder(Long driverId) {
        for (Order order : orderRepository.findAll()) {
            if (order.getDriverId() != null && order.getDriverId().equals(driverId)
                    ) {
                return order;
            }
        }
        return null;
    }

    @Override
    public List<Order> driverFindPastOrders(Long driverId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getDriverId() != null && order.getDriverId().equals(driverId)
                        )
                .collect(
                        Collectors.toList());
    }

    @Override
    public List<Order> restaurantGetActiveOrders(Long restaurantId) {
        return orderRepository.findAll().stream().filter(
                order -> order.getRestaurantId().equals(restaurantId) ).collect(
                Collectors.toList());
    }

    @Override
    public List<Order> restaurantFindPastOrders(Long restaurantId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getRestaurantId().equals(restaurantId) )
                .collect(
                        Collectors.toList());
    }

    @Override
    public int addComment(Long id, int rating, String content) {
        Optional<Order> orderOrNot = getOrder(id);
        if (orderOrNot.isPresent()) {
            Order order = orderOrNot.get();
            if (order.getComment() == null) {
                Comment newComment = new Comment(rating, content);
                order.setComment(newComment);
                orderRepository.save(order);
                System.out.println("Add a comment");
                return 1;
            } else {
                System.out.println("The order already has a comment");
                return 0;
            }
        }
        System.out.println("The order doesn't exist");
        return -1;
    }

    @Override
    public int deleteComment(Long id) {
        Optional<Order> orderOrNot = getOrder(id);
        if (orderOrNot.isPresent()) {
            Order order = orderOrNot.get();
            order.setComment(null);
            orderRepository.save(order);
            System.out.println("Delete a comment");
            return 1;
        }
        System.out.println("The order doesn't exist");
        return -1;
    }

    @Override
    public List<Comment> restaurantGetComments(Long restaurantId) {
        List<Order> temp = this.restaurantFindPastOrders(restaurantId);
        List<Comment> res = new ArrayList<>();
        for (Order o : temp) {
            if (o.getComment() != null) {
                res.add(o.getComment());
            }
        }
        return res;
    }
}
