package com.example.restaurant.restController;

import com.example.restaurant.DTO.BetweenDatesDTO;
import com.example.restaurant.entity.Food;
import com.example.restaurant.entity.Order;
import com.example.restaurant.DTO.OrderDto;
import com.example.restaurant.entity.Status;
import com.example.restaurant.mapper.OrderMapper;
import com.example.restaurant.service.FoodService;
import com.example.restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class OrderRestController {
    private final OrderService service;
    private final FoodService foodService;
    private final OrderMapper orderMapper;

    private Double calculateTotalPrice(List<Food> foods) {
        double totalPrice = 0.0;
        for (Food food : foods) {
            totalPrice += food.getPrice();
        }
        return totalPrice;
    }
    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {

        Order order = orderMapper.orderDtoToEntity(orderDto);
        order.setTotalPrice(calculateTotalPrice(orderDto.command()));

        // Food entities from the database
        List<Food> foods = new ArrayList<>();
        for (Food food : orderDto.command()) {
            Food tmp = foodService.getFoodById(food.getId());
            if (tmp != null) {
                foods.add(tmp);
            } else {
                return ResponseEntity.badRequest().body("Food with ID " + food.getId() + " not found");
            }
        }
        order.setCommand(foods);
        Order savedOrder = service.saveOrder(order);

        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/order/id:{id}")
    public Order findOrderById(@PathVariable int id) {
        Order order = service.getOrderById(id);
        return order;
    }

    @GetMapping("/allOrders")
    public List<Order> getOrders() {
        return  service.getOrders();
    }
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable int id, @RequestBody Map<String, String> requestBody) {

        String status = requestBody.get("status");

        try {
            Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status: " + status);
        }

        Order order = service.getOrderById(id);
        order.setStatus(status);
        return ResponseEntity.ok(service.updateOrder(order));
    }

    @ResponseBody
    @DeleteMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable int id) {
        return service.deleteOrder(id);

    }
    @GetMapping("/allOrdersDates")
    public List<Order> getOrdersDates(
            @RequestBody BetweenDatesDTO find) {
        return service.getOrdersBetweenDates(find.startDate(),find.endDate());
    }

    @GetMapping("/orderOnDate")
    public List<Order> getOrderDate(
            @RequestBody Map<String, String> requestBody) {
        String dateString = requestBody.get("find");
        LocalDate findDate = LocalDate.parse(dateString);
        return service.orderDate(findDate);
    }

    @GetMapping("/topFoods")
    public List<Food> getTopFoods() {
        return service.getTopFoods();
    }


}
