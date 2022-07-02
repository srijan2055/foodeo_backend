package com.foodeo.backend.service;


import com.foodeo.backend.model.Dish;
import com.foodeo.backend.model.RestaurantInfo;
import com.foodeo.backend.model.SearchEngine;
import com.foodeo.backend.repository.SearchEngineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Embedded;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SearchEngineServiceImpl implements SearchEngineService {

    @Autowired
    SearchEngineRepository searchEngineRepository;

    @Embedded
    SearchEngine searchEngine;

    @Override
    public void addRestaurant(String word, Long restaurantId) {
        Optional<SearchEngine> optionalSearchEngine = searchEngineRepository.findById("1");
        if (optionalSearchEngine.isEmpty()) return;
        SearchEngine searchEngine = optionalSearchEngine.get();
        searchEngine.add(word, restaurantId);
        searchEngineRepository.save(searchEngine);
    }

    @Override
    public ArrayList<Long> searchRestaurant(String word) {
        Optional<SearchEngine> optionalSearchEngine = searchEngineRepository.findById("1");
        if (optionalSearchEngine.isEmpty()) return null;
        SearchEngine searchEngine = optionalSearchEngine.get();
        searchEngineRepository.save(searchEngine);
        return (ArrayList<Long>) searchEngine.search(word);
    }

    @Override
    public void removeRestaurant(String word, Long restaurantId) {
        Optional<SearchEngine> optionalSearchEngine = searchEngineRepository.findById("1");
        if (optionalSearchEngine.isEmpty()) return;
        SearchEngine searchEngine = optionalSearchEngine.get();
        searchEngine.remove(word, restaurantId);
        searchEngineRepository.save(searchEngine);
    }

    @Override
    public void eraseInfo(RestaurantInfo info, Long restaurantId) {
        this.removeRestaurant(info.getRestaurantName(), restaurantId);
        this.removeRestaurant(info.getTag1(), restaurantId);
        this.removeRestaurant(info.getTag2(), restaurantId);
        this.removeRestaurant(info.getTag3(), restaurantId);
    }

    @Override
    public void eraseDishes(List<Dish> dishes, Long restaurantId) {
        for (Dish dish : dishes) {
            this.removeRestaurant(dish.getDishName(), restaurantId);
        }
    }

    @Override
    public void updateInfo(RestaurantInfo info, Long restaurantId) {
        this.addRestaurant(info.getRestaurantName(), restaurantId);
        this.addRestaurant(info.getTag1(), restaurantId);
        this.addRestaurant(info.getTag2(), restaurantId);
        this.addRestaurant(info.getTag3(), restaurantId);
    }
}
