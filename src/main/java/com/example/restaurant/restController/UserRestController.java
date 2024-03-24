package com.example.restaurant.restController;

import com.example.restaurant.DTO.UserDto;
import com.example.restaurant.entity.User;
import com.example.restaurant.mapper.UserMapper;
import com.example.restaurant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserRestController {
    @Autowired
    private UserService service;

    private final UserMapper userMapper;

    @PostMapping("/addUser")
    public UserDto addUser(@RequestBody User user) {
        user.setRole("USER");
        service.saveUser(user);
        UserDto userDto = userMapper.userEntityToDto(user);
        return userDto;
    }

    @GetMapping("/user/{id}")
    public UserDto findUserById(@PathVariable int id) {
        UserDto userDto = userMapper.userEntityToDto(service.getUserById(id));
        return userDto;
    }

    @GetMapping("/allUsers")
    public List<UserDto> getAllUsers() {
        return  service.getAllUsers();
    }


    @PutMapping("/updateUserFields")
    public UserDto updateUser(@RequestBody User user) {
        UserDto userDto = userMapper.userEntityToDto(service.updateUser(user));
        return userDto;
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        return service.deleteUser(id);
    }
}
