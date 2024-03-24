package com.example.restaurant.service;

import com.example.restaurant.DTO.UserDto;
import com.example.restaurant.entity.User;
import com.example.restaurant.mapper.UserMapper;
import com.example.restaurant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.restaurant.Utils.PasswordHashing;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository repository;
    private final UserMapper userMapper;

    public boolean setActive(User user) {
        if(!findActive())
        {
            user.setActive(true);
            repository.save(user);
            return true;
        }
        return false;
    }
    public void logOut() {
        List<User> users = getUsers();
        for (User user : users) {
            user.setActive(false);
            repository.save(user);
        }
    }
    public boolean findActive() {
        return getUsers().stream().anyMatch(User::isActive);
    }
    public User saveUser(User user) {
       // PasswordHashing.bytesToHex(user.getPassword().to);
        user.setPassword(PasswordHashing.bytesToHex(user.getPassword().getBytes()));
        if(isAdminActive()){
            user.setRole(user.getRole().toString());
        }
        //user.setPassword(PasswordHashing.bytesToHex(user.getPassword().getBytes()));
        else{
            user.setRole("USER");
        }
        return repository.save(user);
    }
    public User updatePass(User user) {
        user.setPassword(PasswordHashing.bytesToHex(user.getPassword().getBytes()));
        return repository.save(user);
    }
    public void registerUser(User user) {
        user.setPassword(PasswordHashing.bytesToHex(user.getPassword().getBytes()));
        user.setRole("USER");
        repository.save(user);
    }
    public void registerAdmin(User user) {
        user.setPassword(PasswordHashing.bytesToHex(user.getPassword().getBytes()));
        user.setRole("ADMIN");
        repository.save(user);
    }
    public boolean validateUserCredentials(String username, String password) {
        User user = repository.findByUsername(username);
        if (user != null) {
            String hashedPassword = PasswordHashing.bytesToHex(password.getBytes());
            return hashedPassword.equals(user.getPassword());
        }
        return false;
    }

    public List<User> getUsers() {return repository.findAll();}
    public List<UserDto> getAllUsers(){
        return userMapper.userListEntityToDto(repository.findAll());
    }

    public User getUserById(int userId) {
        return repository.findById(userId).orElse(null);
    }

    public User getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public boolean isAdminActive(){return getUsers().stream()
            .anyMatch(user -> "admin".equalsIgnoreCase(user.getRole()) && user.isActive());}
    public String deleteUser(int userId) {
        Optional<User> optionalUser = repository.findById(userId);
        if(optionalUser.isEmpty()){
            return "No user found with this ID";
        }

        String role ="";
        role = repository.findById(userId).orElse(null).getRole();
        if(!Objects.equals(role, "ADMIN")) {
            if(isAdminActive()) {
                repository.deleteById(userId);
                return "User with id " + userId + " removed! ";
            }else{return "Only admins can delete users";}
        }
        else
        { return "Admins can not be deleted";}

    }

    public User updateUser(User user) {
        User existingUser = repository.findById(user.getId()).orElse(null);

        if(existingUser != null){
            if(existingUser.isActive()){
                updatePass(existingUser);
                existingUser.setEmailAddress(user.getEmailAddress());
                return repository.save(existingUser);
            }
            else if(isAdminActive()) {
                existingUser.setEmailAddress(user.getEmailAddress());
                return repository.save(existingUser);
            }
        }

        return null;

    }
}
