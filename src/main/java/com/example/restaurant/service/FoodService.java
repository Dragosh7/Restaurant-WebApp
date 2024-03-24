package com.example.restaurant.service;

import com.example.restaurant.entity.Food;
import com.example.restaurant.repository.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodService {
    @Autowired
    private FoodRepository repository;
    private final OrderService service;

    public Food saveFood(Food food) {
        return repository.save(food);
    }

    public List<Food> saveFoods(List<Food> foods) {
        return repository.saveAll(foods);
    }

    public List<Food> getFoods() {
        return repository.findAll();
    }

    public Food getFoodById(int foodId) {
        return repository.findById(foodId).orElse(null);
    }

    public Food getFoodByName(String name) {
        return repository.findByName(name);
    }

    public String deleteFood(int foodId) {
        Food food = repository.findById(foodId).orElse(null);
        if (food == null) {
            return "Food with id " + foodId + " does not exist";
        }
        boolean isFoodMappedToOrder = service.isFoodMappedToOrder(foodId);
        if (isFoodMappedToOrder) {
            return "Cannot delete food with id " + foodId + " because it is mapped to one or more orders";
        }
        repository.deleteById(foodId);
        return "Food with id " + foodId + " removed from the menu because no one eats it";
    }

    public Food updateFood(Food food) {
        Food existingFood=repository.findById(food.getId()).orElse(null);
        existingFood.setName(food.getName());
        existingFood.setPrice(food.getPrice());
        existingFood.setStock(food.getStock());
        return repository.save(existingFood);
    }
    public boolean decreaseStock(int foodId) {
        Food food = repository.findById(foodId).orElse(null);
        if (food != null) {
            int currentStock = food.getStock();
            if (currentStock > 0) {
                food.setStock(currentStock - 1);
                repository.save(food);
                return true;
            } else {
                return false;
                //throw new RuntimeException("Stock for food item with ID " + foodId + " is already empty");

            }
        } else {
            return false;
            //throw new RuntimeException("Food item with ID " + foodId + " not found");
        }
    }
}
