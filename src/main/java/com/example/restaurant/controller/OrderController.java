package com.example.restaurant.controller;

import com.example.restaurant.entity.Food;
import com.example.restaurant.entity.Status;
import com.example.restaurant.service.FoodService;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService service;
    @Autowired
    private FoodService foodService;

    @GetMapping("/addOrder")
    public String newOrder(Model model,@ModelAttribute("errorMessage") String errorMessage) {
        List<Food> foodList = foodService.getFoods();
        model.addAttribute("foodList", foodList);
        model.addAttribute("order", new Order());
        model.addAttribute("errorMessage", errorMessage);
        return "/newOrder";

    }
    @PostMapping("/addOrder")
    public String addOrder(@RequestParam(value="selectedFoodIds", required = true, defaultValue = "") List<Integer> selectedFoodIds,
                           @RequestParam(value="hiddenTotalPrice",required = true, defaultValue = "0") Double totalPrice,
                           @RequestParam("status") String status,
                           @RequestParam("date") LocalDate localDate, RedirectAttributes redirectAttributes){//,
        //@ModelAttribute Order order) {
        if (selectedFoodIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Order creating error, make sure something is selected");

            return "redirect:/addOrder";
        }
        Order order=new Order();
        //Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //System.out.println("Converted Date: " + date);


        order.setTotalPrice(totalPrice);
        order.setDate(localDate);
        order.setStatus(status);

        List<Food> selectedFoods = new ArrayList<>();
        for (Integer foodId : selectedFoodIds) {
            Food food = foodService.getFoodById(foodId);

            if(foodService.decreaseStock(foodId)){
            selectedFoods.add(food);}
        }
        if (selectedFoods.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "No more available");

            return "redirect:/addOrder";
        }
        order.setCommand(selectedFoods);
        service.saveOrder(order);

        return "redirect:/ordersList";
    }
    @PostMapping("/placeOrder")
    public String placeOrder(@RequestParam("food") int foodId, @RequestParam("quantity") int quantity) {

        Food food = foodService.getFoodById(foodId);

        return "redirect:/orderConfirmation";
    }

    @GetMapping("/ordersList")
    public String listOrders(Model model) {
        List<Order> orders = service.getOrders();
        model.addAttribute("orders", orders);
        return "/orderList";
    }
    @GetMapping("/orders")
    public List<Order> findAllOrders() {
        return service.getOrders();
    }

    @GetMapping("/order/{id}")
    public Order findOrderById(@PathVariable int orderId) {
        return service.getOrderById(orderId);
    }

    @GetMapping("/order/{status}")
    public Order findOrderByName(@PathVariable Status status) {
        return service.getOrderByStatus(status);
    }

    @PutMapping("/updateOrder")
    public Order updateOrder(@RequestBody Order order) {
        return service.updateOrder(order);
    }

}
