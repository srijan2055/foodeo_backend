package com.foodeo.backend.service;


import com.foodeo.backend.model.Customer;
import com.foodeo.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements UserService<Customer> {


    @Autowired
    CustomerRepository customerRepository;



    PasswordService passwordService = new PasswordService();

    @Override
    public Customer addUser(String userName, String password, String phoneNumber, String address
    ) {
        if (this.getUserIdByName(userName) == null) {
            String newPassword = passwordService.generatePassword(password);
            Customer customer = new Customer(userName, newPassword, phoneNumber, address);
            customerRepository.save(customer);
            System.out.println("Customer added to the database");
            return customer;
        }
        System.out.println("Customer can't be added to the database");
        return null;
    }

    @Override
    public int deleteUser(Long id) {
        if (this.getUser(id).isPresent()) {
            customerRepository.deleteById(id);
            System.out.println("Customer deleted from the database");
            return 1;
        }
        System.out.println("Customer can't be deleted from the database");
        return -1;
    }

    @Override
    public Optional<Customer> getUser(Long id) {
        if (id != null) {
            return customerRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public Long getUserIdByName(String userName) {
        List<Customer> customers = this.getUsers();
        for (Customer customer : customers) {
            if (customer.getUserName().equals(userName)) {
                return customer.getId();
            }
        }
        System.out.println("Given userName doesn't found in customer database");
        return null;
    }

    @Override
    public Optional<Customer> getUserByName(String userName) {
        return this.getUser(getUserIdByName(userName));
    }

    @Override
    public List<Customer> getUsers() {
        return customerRepository.findAll();
    }

    @Override
    public boolean passwordMatch(Long id, String password) {
        Optional<Customer> customer = this.getUser(id);
        return customer.isPresent() && passwordService.passwordMatch(password, customer.get().getPassword());
    }

    @Override
    public int updatePassword(Long id, String oldPassword, String newPassword) {
        Optional<Customer> customer = this.getUser(id);
        if (customer.isPresent()) {
            if (this.passwordMatch(id, oldPassword)) {
                customer.get().setPassword(passwordService.generatePassword(newPassword));
                customerRepository.save(customer.get());
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
        Optional<Customer> customer = this.getUser(id);
        if (customer.isPresent()) {
            customer.get().setPhoneNumber(newNumber);
            customerRepository.save(customer.get());
            System.out.println("Update the phone number");
            return 1;
        }
        System.out.println("Can't update the phone number");
        return -1;
    }

    @Override
    public int updateAddress(Long id, String address) {
        Optional<Customer> customer = this.getUser(id);
        if (customer.isPresent()) {
            customer.get().setAddress(address);
            customerRepository.save(customer.get());
            System.out.println("Update the address");
            return 1;
        }
        System.out.println("Can't update the address");
        return -1;
    }
}
