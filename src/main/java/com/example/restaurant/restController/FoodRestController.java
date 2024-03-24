package com.example.restaurant.restController;

import com.example.restaurant.entity.Food;
import com.example.restaurant.service.FoodService;
import com.example.restaurant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FoodRestController {

    private final FoodService service;
    private final UserService userService;


    @PostMapping("/addFood")
    public ResponseEntity<?> addFood(@RequestBody Food food) {
        Food tmp=service.getFoodByName(food.getName());
        if(tmp == null) {
            if (userService.isAdminActive()) {
                return ResponseEntity.ok(service.saveFood(food));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can add a new type of food");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This food already exists");
    }

    @GetMapping("/food/id:{id}")
    public Food  findFoodById(@PathVariable int id) {
        return service.getFoodById(id) ;
    }

    @GetMapping("/food/name:{name}")
    public Food findFoodByName(@PathVariable String name) {
        return service.getFoodByName(name);
    }

    @GetMapping("/allFoods")
    public List<Food > getAllFoods() {
        return  service.getFoods();
    }

    @PutMapping("/updateFoodFields")
    public ResponseEntity<?> updateFood(@RequestBody Food food) {
        if(userService.isAdminActive()){
            return  ResponseEntity.ok(service.updateFood(food));
        }
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can add a new type of food");

    }

    @DeleteMapping("/deleteFood/{id}")
    public String deleteFood(@PathVariable int id) {
        return service.deleteFood(id);
    }

    @PostMapping("/addFoods")
    public List<Food> addFoods(@RequestBody List<Food> foods) {
        return service.saveFoods(foods);
    }


}
