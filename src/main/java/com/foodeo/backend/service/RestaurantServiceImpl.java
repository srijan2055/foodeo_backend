package com.foodeo.backend.service;


import com.foodeo.backend.model.Dish;
import com.foodeo.backend.model.Restaurant;
import com.foodeo.backend.model.RestaurantInfo;
import com.foodeo.backend.repository.DishRepository;
import com.foodeo.backend.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantServiceImpl implements RestaurantService, UserService<Restaurant> {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
            DishRepository dishRepository;


    PasswordService passwordService = new PasswordService();
    private Long id;
    private Dish dish;


    @Override
    public int addDish(Long id, Dish dish) {
        this.id = id;
        this.dish = dish;
        Optional<Restaurant> restaurant = this.getUser(id);
        System.out.println(restaurant.isPresent());
        if (restaurant.isPresent()) {
            Set<Dish> set;
            if (restaurant.get().getMenu() == null) {
                set = new HashSet<>();
            } else {
                set = new HashSet<>(restaurant.get().getMenu());
            }
            System.out.println(dish + "in set");
            set.add(dish);
            restaurant.get().setMenu(new ArrayList<>(set));
            System.out.println(restaurant.get().getMenu() + "after setting menu");


            restaurantRepository.save(restaurant.get());
            System.out.println(dish);
            dish.setRestaurantId(restaurant.get().getId());
            dishRepository.save(dish);


            System.out.println("Add the dish");
            return 1;
        }
        System.out.println("Can't add the dish");
        return -1;
    }

    @Override
    public int removeDish(Long id, Dish dish) {
        Optional<Restaurant> restaurant = this.getUser(id);
        if (restaurant.isPresent()) {
            List<Dish> temp = restaurant.get().getMenu();
            if (temp.contains(dish)) {
                temp.remove(dish);
                restaurant.get().setMenu(temp);
                restaurantRepository.save(restaurant.get());

                System.out.println("Remove the dish");
                return 1;
            } else {
                System.out.println("Dish not in the menu");
                return 0;
            }
        }
        System.out.println("Can't remove the dish");
        return -1;
    }

    @Override
    public List<Dish> getAllDishes(Long id) {
        Optional<Restaurant> restaurant = this.getUser(id);
        System.out.println("Get all dishes from restaurant: " + id);
    //    return restaurant.map(Restaurant::getMenu).orEls  e(null);
       return dishRepository.findAll();
    }

    @Override
    public RestaurantInfo getInformation(Long id) {
        Optional<Restaurant> restaurant = this.getUser(id);
        if (restaurant.isPresent()) {
            System.out.println("Get the restaurant information");
            if (restaurant.get().getInformation() == null) {
                return new RestaurantInfo();
            } else {
                return restaurant.get().getInformation();
            }
        }
        return null;
    }

    @Override
    public int updateInfo(Long id, RestaurantInfo info) {
        Optional<Restaurant> restaurant = this.getUser(id);
        if (restaurant.isPresent()) {

            restaurant.get().setInformation(info);
            restaurantRepository.save(restaurant.get());
            System.out.println("Update the information");
            return 1;
        }
        System.out.println("Can't update the information");
        return -1;
    }

    @Override
    public Restaurant addUser(String userName, String password, String phoneNumber, String address
    ) {
        if (this.getUserIdByName(userName) == null) {
            String newPassword = passwordService.generatePassword(password);
            Restaurant restaurant = new Restaurant(userName, newPassword, phoneNumber, address);
            restaurantRepository.save(restaurant);
            System.out.println("Restaurant added to the database");
            return restaurant;
        }
        System.out.println("Restaurant can't be added to the database");
        return null;
    }

    @Override
    public int deleteUser(Long id) {
        if (this.getUser(id).isPresent()) {
            restaurantRepository.deleteById(id);
            System.out.println("Restaurant deleted from the database");
            return 1;
        }
        System.out.println("Restaurant can't be deleted from the database");
        return -1;
    }

    @Override
    public Optional<Restaurant> getUser(Long id) {
        if (id != null) {
            return restaurantRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public Long getUserIdByName(String userName) {
        List<Restaurant> restaurants = this.getUsers();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getUserName().equals(userName)) {
                return restaurant.getId();
            }
        }
        System.out.println("Given userName doesn't found in restaurant database");
        return null;
    }

    @Override
    public Optional<Restaurant> getUserByName(String userName) {
        return this.getUser(getUserIdByName(userName));
    }

    @Override
    public List<Restaurant> getUsers() {
        return restaurantRepository.findAll();
    }

    @Override
    public boolean passwordMatch(Long id, String password) {
        Optional<Restaurant> restaurant = this.getUser(id);
        return restaurant.isPresent() && passwordService
                .passwordMatch(password, restaurant.get().getPassword());
    }

    @Override
    public int updatePassword(Long id, String oldPassword, String newPassword) {
        Optional<Restaurant> restaurant = this.getUser(id);
        if (restaurant.isPresent()) {
            if (this.passwordMatch(id, oldPassword)) {
                restaurant.get().setPassword(passwordService.generatePassword(newPassword));
                restaurantRepository.save(restaurant.get());
                System.out.println("Update the password");
                return 1;
            } else {
                System.out.println("Password doesn't match");
                return 0;
            }
        }
        System.out.println("Can't update the password");
        return -1;
    }

    @Override
    public int updatePhoneNumber(Long id, String newNumber) {
        Optional<Restaurant> restaurant = this.getUser(id);
        if (restaurant.isPresent()) {
            restaurant.get().setPhoneNumber(newNumber);
            restaurantRepository.save(restaurant.get());
            System.out.println("Update the phone number");
            return 1;
        }
        System.out.println("Can't update the phone number");
        return -1;
    }

    @Override
    public int updateAddress(Long id, String address) {
        Optional<Restaurant> restaurant = this.getUser(id);
        if (restaurant.isPresent()) {
            restaurant.get().setAddress(address);

            restaurantRepository.save(restaurant.get());
            System.out.println("Update the address");
            return 1;
        }
        System.out.println("Can't update the address");
        return -1;
    }
}
