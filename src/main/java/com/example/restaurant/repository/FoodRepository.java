package com.example.restaurant.repository;

import com.example.restaurant.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food,Integer> {
    Food findByName(String name);
}
