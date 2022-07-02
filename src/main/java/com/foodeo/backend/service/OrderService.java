package com.foodeo.backend.service;


import com.foodeo.backend.model.Comment;
import com.foodeo.backend.model.Dish;
import com.foodeo.backend.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    default int addOrderToCart(Long customerId, Long restaurantId, List<Dish> content) {

        return 0;
    }

    // Order can only be canceled when in cart or not in delivery
    int checkoutOrder(Long id);

    // checkout all orders in the shopping cart
    int checkoutAll(List<Order> orders);

    int cancelOrder(Long id);

    Optional<Order> getOrder(Long id);

    // Driver accept the order, the order can only be accepted if it already started and not in delivery
    int acceptOrder(Long id, Long driverId);

    // Driver finish the order, the order can only be finished if it is in delivery and not finished
    int finishOrder(Long id);

    // Customer check orders in the cart
    List<Order> customerCart(Long customerId);

    // Customer check all active orders, no matter there is or isn't a driver and the order isn't finished
    List<Order> customerGetActiveOrders(Long customerId);

    // Customer check order history
    List<Order> customerFindPastOrders(Long customerId);

    // Driver check all orders that waiting for a driver
    List<Order> getAllPendingOrders();

    // Driver get current order
    Order driverGetActiveOrder(Long driverId);

    // Driver check order history
    List<Order> driverFindPastOrders(Long driverId);

    // Restaurant check all active orders, no matter there is or isn't a driver
    List<Order> restaurantGetActiveOrders(Long restaurantId);

    // Restaurant check order history
    List<Order> restaurantFindPastOrders(Long restaurantId);

    int addComment(Long id, int rating, String content);

    int deleteComment(Long id);

    List<Comment> restaurantGetComments(Long restaurantId);
}
