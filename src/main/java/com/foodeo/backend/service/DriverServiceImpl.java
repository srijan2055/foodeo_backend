package com.foodeo.backend.service;


import com.foodeo.backend.model.Driver;
import com.foodeo.backend.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements UserService<Driver> {

    @Autowired
    DriverRepository driverRepository;
    PasswordService passwordService = new PasswordService();

    @Override
    public Driver addUser(String userName, String password, String phoneNumber, String address) {
        if (this.getUserIdByName(userName) == null) {
            String newPassword = passwordService.generatePassword(password);
            Driver driver = new Driver(userName, newPassword, phoneNumber, address);
            driverRepository.save(driver);
            System.out.println("Driver added to the database");
            return driver;
        }
        System.out.println("Driver can't be added to the database");
        return null;
    }

    @Override
    public int deleteUser(Long id) {
        if (this.getUser(id).isPresent()) {
            driverRepository.deleteById(id);
            System.out.println("Driver deleted from the database");
            return 1;
        }
        System.out.println("Driver can't be deleted from the database");
        return -1;
    }

    @Override
    public Optional<Driver> getUser(Long id) {
        if (id != null) {
            return driverRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public Long getUserIdByName(String userName) {
        List<Driver> drivers = this.getUsers();
        for (Driver driver : drivers) {
            if (driver.getUserName().equals(userName)) {
                return driver.getId();
            }
        }
        System.out.println("Given userName doesn't found in driver database");
        return null;
    }

    @Override
    public Optional<Driver> getUserByName(String userName) {
        return this.getUser(getUserIdByName(userName));
    }

    @Override
    public List<Driver> getUsers() {
        return driverRepository.findAll();
    }

    @Override
    public boolean passwordMatch(Long id, String password) {
        Optional<Driver> driver = this.getUser(id);
        return driver.isPresent() && passwordService.passwordMatch(password, driver.get().getPassword());
    }

    @Override
    public int updatePassword(Long id, String oldPassword, String newPassword) {
        Optional<Driver> driver = this.getUser(id);
        if (driver.isPresent()) {
            if (this.passwordMatch(id, oldPassword)) {
                driver.get().setPassword(passwordService.generatePassword(newPassword));
                driverRepository.save(driver.get());
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
        Optional<Driver> driver = this.getUser(id);
        if (driver.isPresent()) {
            driver.get().setPhoneNumber(newNumber);
            driverRepository.save(driver.get());
            System.out.println("Update the phone number");
            return 1;
        }
        System.out.println("Can't update the phone number");
        return -1;
    }

    @Override
    public int updateAddress(Long id, String address) {
        Optional<Driver> driver = this.getUser(id);
        if (driver.isPresent()) {
            driver.get().setAddress(address);

            driverRepository.save(driver.get());
            System.out.println("Update the address");
            return 1;
        }
        System.out.println("Can't update the address");
        return -1;
    }
}
