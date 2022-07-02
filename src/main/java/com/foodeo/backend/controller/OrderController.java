package com.foodeo.backend.controller;

import com.foodeo.backend.exception.CommentAlreadyExistException;
import com.foodeo.backend.exception.OrderAlreadyCheckoutException;
import com.foodeo.backend.exception.OrderAlreadyDeliverException;
import com.foodeo.backend.exception.OrderNotExistException;
import com.foodeo.backend.model.Dish;
import com.foodeo.backend.model.Order;
import com.foodeo.backend.service.OrderServiceImpl;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/addToCart")
    public int addOrderToCart(@RequestBody String jsonOrder) {
        JSONObject order = new JSONObject(jsonOrder);
        Long customerId = order.getLong("customerId");
        Long restaurantId = order.getLong("restaurantId");
        JSONArray shopcart = order.getJSONArray("shopcart");
        Gson gson = new Gson();
        List<Dish> list = new ArrayList<>();
        for (Object object : shopcart) {
            list.add(gson.fromJson(object.toString(), Dish.class));
        }
        System.out.println(customerId +""+ restaurantId +""+ list);
        return orderService.addOrderToCart(customerId, restaurantId, list);
    }

    @DeleteMapping(path = "{id}")
    public int deleteOrder(@PathVariable("id") Long id)
            throws OrderNotExistException, OrderAlreadyDeliverException {
        int res = orderService.cancelOrder(id);
        if (res == 0) {
            throw new OrderNotExistException("Order doesn't exist");
        }
        if (res == -1) {
            throw new OrderAlreadyDeliverException(
                    "Can't cancel order. It is either in delivery or finished");
        }
        return res;
    }

    @PostMapping(path = "/checkoutAll")
    public int checkoutUsers(@RequestBody String jsonOrders)
            throws OrderNotExistException, OrderAlreadyCheckoutException {
        JSONObject orders = new JSONObject((jsonOrders));
        JSONArray orderList = orders.getJSONArray("orders");
        Gson gson = new Gson();
        List<Order> list = new ArrayList<>();
        for (Object object : orderList) {
            list.add(gson.fromJson(object.toString(), Order.class));
        }
        int res = orderService.checkoutAll(list);
        if (res == 0) {
            throw new OrderNotExistException("Order doesn't exist");
        }
        if (res == -1) {
            throw new OrderAlreadyCheckoutException("Order already checkout");
        }
        return res;
    }

    @PostMapping(path = "/addComment")
    public int addComment(@RequestBody String jsonOrder)
            throws CommentAlreadyExistException, OrderNotExistException {
        JSONObject order = new JSONObject(jsonOrder);
        Long orderId = order.getLong("orderId");
        int rating = order.getInt("rating");
        String content = order.getString("content");
        int res = orderService.addComment(orderId, rating, content);
        if (res == 0) throw new CommentAlreadyExistException("Each order can only has one comment");
        if (res == -1) throw new OrderNotExistException("Order doesn't exist");
        return res;
    }

    @DeleteMapping(path = "/deleteComment/" + "{id}")
    public int deleteComment(@PathVariable("id") Long id) throws OrderNotExistException {
        int res = orderService.deleteComment(id);
        if (res == -1) throw new OrderNotExistException("Order doesn't exist");
        return res;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({OrderNotExistException.class, OrderAlreadyDeliverException.class,
            OrderAlreadyCheckoutException.class, CommentAlreadyExistException.class})
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
