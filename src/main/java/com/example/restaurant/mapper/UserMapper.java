package com.example.restaurant.mapper;

import com.example.restaurant.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.restaurant.entity.User;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper {

    public UserDto userEntityToDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .emailAddress(user.getEmailAddress())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }

    public List<UserDto> userListEntityToDto(List<User> users){
        return users.stream()
                .map(user -> userEntityToDto(user))
                .toList();
    }


}


