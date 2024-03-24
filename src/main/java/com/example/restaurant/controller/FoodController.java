package com.example.restaurant.controller;

import com.example.restaurant.entity.*;
import com.example.restaurant.service.FoodService;
import com.example.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@Controller
public class FoodController {
    @Autowired
    private FoodService service;
    @Autowired
    private UserService userService;

    @GetMapping("/newFood")
    public String newFood(Model model, Principal principal) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       // String name = auth.getName(); //get logged in username
        //System.out.println(name);
        if (principal != null) {
            String loggedInUsername = principal.getName();

            User user = userService.getUserByUsername(loggedInUsername);
            if (user != null) {
                if (user.getRole().equals("ADMIN")) {
                    model.addAttribute("food", new Food());
                    return "newFood";
                }
                return "redirect:/foodList";
            }
            return "newFood";
        }
        return "newFood";


    }
    @PostMapping("/newFood")
    public String addFood(@ModelAttribute Food food , ModelMap model) {

        service.saveFood(food);

        return "redirect:/foodList";
    }
    @GetMapping("/foodList")
    public String listFood( ModelMap model) {

        List<Food> foods = service.getFoods();
        model.addAttribute("foods", foods);
        return "foodList";
    }


}
