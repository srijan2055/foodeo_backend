package com.foodeo.backend.service;


import java.util.List;
import java.util.Optional;

public interface UserService<T> {

    T addUser(String userName, String password, String phoneNumber, String address);

    int deleteUser(Long id);

    Optional<T> getUser(Long id);

    Long getUserIdByName(String userName);

    Optional<T> getUserByName(String userName);

    List<T> getUsers();

    boolean passwordMatch(Long id, String password);

    int updatePassword(Long id, String oldPassword, String newPassword);

    int updatePhoneNumber(Long id, String newNumber);

    int updateAddress(Long id, String address);
}
