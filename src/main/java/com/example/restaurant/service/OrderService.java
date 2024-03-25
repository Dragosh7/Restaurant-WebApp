package com.example.restaurant.service;

import com.example.restaurant.entity.Food;
import com.example.restaurant.entity.Order;
import com.example.restaurant.entity.Status;
import com.example.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public boolean isFoodMappedToOrder(int foodId) {
        return repository.existsOrderByFoodId(foodId);
    }

    public Order saveOrder(Order order) {
        order.setStatus(order.getStatus().toString());
        return repository.save(order);
    }

    public List<Order> getOrders() {
        return repository.findAll();
    }

    public Order getOrderById(int orderId) {
        return repository.findById(orderId).orElse(null);
    }

    public Order getOrderByStatus(Status name) {
        return repository.findByStatus(name);
    }

    public String deleteOrder(int orderId) {
        if(getOrderById(orderId)!=null){
        repository.deleteById(orderId);
        return "Order with id " + orderId + " removed! ";}
        else {
            return "Order with this id:" + orderId+" does not exist";
        }
    }

    public Order updateOrder(Order order) {
        Order existingOrder = repository.findById(order.getId()).orElse(null);
        existingOrder.setCommand(order.getCommand());
        existingOrder.setTotalPrice(order.getTotalPrice());
        existingOrder.setStatus(order.getStatus());
        return repository.save(existingOrder);
    }
    public List<Order> getOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        return repository.findByDateBetween(startDate, endDate);
    }
    public List<Order> orderDate(LocalDate date) {
        return repository.selectByDate(date);
    }

    public List<Food> getTopFoods() {
        return repository.findMostOrderedFoods();
    }

}
