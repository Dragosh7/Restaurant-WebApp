package com.example.restaurant.repository;

import com.example.restaurant.entity.Food;
import com.example.restaurant.entity.Order;
import com.example.restaurant.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findByStatus(Status name);
    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.command f WHERE f.id = :foodId")
    boolean existsOrderByFoodId(@Param("foodId") int foodId);

    //@Query("SELECT o FROM Order o WHERE o.date BETWEEN :startDate AND :endDate")
    List<Order> findByDateBetween(@Param("startDate")LocalDate startDate,@Param("endDate") LocalDate endDate);

    @Query("SELECT o FROM Order o WHERE o.date = :date")
    List<Order> selectByDate(@Param("date") LocalDate date);

    @Query("SELECT f, COUNT(o) AS numOrders " +
            "FROM Order o " +
            "JOIN o.command f " +
            "GROUP BY f " +
            "ORDER BY numOrders DESC")
    List<Food> findMostOrderedFoods();
}
