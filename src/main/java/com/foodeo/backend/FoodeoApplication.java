package com.foodeo.backend;

import com.foodeo.backend.repository.CustomerRepository;
import com.foodeo.backend.repository.SearchEngineRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.foodeo.backend.repository")
@ComponentScan



public class FoodeoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodeoApplication.class, args);
    }

}
