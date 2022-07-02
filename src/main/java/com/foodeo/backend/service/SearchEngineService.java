package com.foodeo.backend.service;


import com.foodeo.backend.model.Dish;
import com.foodeo.backend.model.RestaurantInfo;

import java.util.ArrayList;
import java.util.List;

public interface SearchEngineService {
    void addRestaurant(String word, Long restaurantId);

    ArrayList<Long> searchRestaurant(String word);

    void removeRestaurant(String word, Long restaurantId);

    void eraseInfo(RestaurantInfo info, Long restaurantId);

    void eraseDishes(List<Dish> dishes, Long restaurantId);

    void updateInfo(RestaurantInfo info, Long restaurantId);
}
