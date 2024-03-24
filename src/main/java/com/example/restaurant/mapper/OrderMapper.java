package com.example.restaurant.mapper;

import com.example.restaurant.DTO.OrderDto;
import com.example.restaurant.entity.Order;
import com.example.restaurant.entity.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    public Order orderDtoToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setCommand(orderDto.command());
        order.setStatus(orderDto.status());
        order.setDate(orderDto.date());

        double totalPrice = orderDto.command().stream()
                .mapToDouble(Food::getPrice)
                .sum();
        order.setTotalPrice(totalPrice);

        return order;
    }
}
