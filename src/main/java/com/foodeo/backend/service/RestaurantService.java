package com.foodeo.backend.service;


import com.foodeo.backend.model.Dish;
import com.foodeo.backend.model.RestaurantInfo;

import java.util.List;

public interface RestaurantService {

    int addDish(Long id, Dish dish);

    int removeDish(Long id, Dish dish);

    List<Dish> getAllDishes(Long id);

    RestaurantInfo getInformation(Long id);

    int updateInfo(Long id, RestaurantInfo info);
}
