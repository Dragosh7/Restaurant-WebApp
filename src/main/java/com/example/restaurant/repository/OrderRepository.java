package com.example.restaurant.repository;

import com.example.restaurant.entity.Order;
import com.example.restaurant.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findByStatus(Status name);
    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.command f WHERE f.id = :foodId")
    boolean existsOrderByFoodId(@Param("foodId") int foodId);

}
