package com.example.restaurant.controller;

import com.example.restaurant.entity.User;
import com.example.restaurant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
    @PostMapping("/logout")
    public String logout(Model model) {
        service.logOut();
        model.addAttribute("error","Successfully logged out");
        return "login";
    }
    @GetMapping("/login")
    public String home(Model model)
    {
        model.addAttribute("user",new User());
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (service.validateUserCredentials(username, password)) {
            boolean anyActiveUsers = service.findActive();

            if (!anyActiveUsers) {
                service.setActive(service.getUserByUsername(username));
                return "redirect:/home";
            } else {
                model.addAttribute("error", "Log out first");
                return "login";
            }
        } else {
            model.addAttribute("error", "Password Incorrect");
            return "login";
        }
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        service.registerUser(user);
        return "redirect:/home";
    }
    @GetMapping("/registerAdmin")
    public String showRegistrationFormAdmin(Model model) {
        model.addAttribute("user", new User());
        return "registerAdmin";
    }

    @PostMapping("/registerAdmin")
    public String registerAdmin(@ModelAttribute("user") User user) {
        service.registerAdmin(user);
        return "redirect:/login";
    }
    @GetMapping("/users")
    public String findAllUsers(Model model) {
        model.addAttribute("users",service.getAllUsers());
        return "users";
    }

    /*
    @PutMapping("/updateUser")
    public String updateUser(@RequestBody User user) {
         service.updateUser(user);
         return "success";
    }
    */
    @RequestMapping("/updateUser/{userID}")
    public String displayUserUpdate(@PathVariable("userID") String userID, Model model) {
        model.addAttribute("user", service.getUserByUsername(userID));
        return "update";
    }


}
