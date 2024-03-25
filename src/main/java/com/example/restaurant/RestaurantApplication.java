package com.example.restaurant;

import com.example.restaurant.Interface.LoginFrame;
import com.example.restaurant.repository.OrderRepository;
import com.example.restaurant.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Date;


@SpringBootApplication
public class RestaurantApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);

    }
    @Bean
    CommandLineRunner init(OrderService service, OrderRepository repository) {
        return args -> {
            LocalDate startDate = LocalDate.of(2024, 3, 12);
            LocalDate endDate = LocalDate.of(2025, 3, 13);
            Date startDateAsDate = java.sql.Date.valueOf(startDate);
            Date endDateAsDate = java.sql.Date.valueOf(endDate);

            System.out.println(service.getOrdersBetweenDates(startDate,endDate));

            System.out.println(service.orderDate(startDate));
            System.out.println(repository.findMostOrderedFoods());
        };

    }

}
